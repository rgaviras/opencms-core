# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

OpenCms is a Java/XML web content management system (Alkacon Software). This repo is the core product: a large monolithic Gradle project with a custom, non-standard source layout (predates Maven-style conventions) plus a set of "core" OpenCms modules (VFS-deployed content/config packages, not Gradle subprojects).

Requires Java 11 or 17. Build is Gradle-based (wrapper included, target Gradle 7.5.1).

## Build commands

Always use the wrapper, not a system `gradle`:

```
./gradlew <task>
```

Common tasks:

- `./gradlew war` — builds the full deployable `opencms.war` (depends on `setupJar`, `resourcesJar`, `modulesJar`, `allModules`; this is the "build everything" task and is slow).
- `./gradlew allModules` — builds ZIPs for every OpenCms module under `modules/` (tasks `dist_<moduleName>` per module, driven by `modules_list` in `build-default.properties`).
- `./gradlew install` — installs to the local Maven repo.
- `./gradlew javadoc` (and `javadocModules`, `javadocGwt`, `javadocSetup`, `javadocTest`) — per-source-set javadoc. Skip all with `-Pskip_javadoc=true`.
- `./gradlew gwt_org.opencms.ade.OpenCms` (and similarly `gwt_org.opencms.ugc.Ugc`, `gwt_org.opencms.ui.WidgetSet`) — GWT-compiles a specific client module listed in `src-gwt/gwt-modules.properties`.

Useful properties (pass as `-Pname=value`):
- `build_directory` (default `../BuildCms`) — where build output goes, set in `build-default.properties`/`gradle.properties`.
- `max_heap_size` (default `1024m`)
- `opencms_version` — overrides the version read from `src/org/opencms/main/version.properties`.
- `external_directories` — points at an external modules project (adds an `extmodules` subproject); only relevant when developing custom modules alongside the core.

Local overrides belong in `gradle.properties` (already present, tracked) — don't hardcode developer-specific paths into `build.gradle` or `build-default.properties`.

## Tests

Tests live under `test/` (source set `test`, not `src/test/java`) and `test-gwt/` (source set `testGwt`, for GWT client tests). Test classes are **not** auto-discovered (`scanForTestClasses = false`); they run only via explicit suite classes named `AllTests.java` in each package (e.g. `test/org/opencms/file/AllTests.java`, `test/org/opencms/test/AllTests.java`).

- `./gradlew test` — runs the full suite via `org.opencms.test.AllTests`.
- `./gradlew testSingle --tests "org.opencms.main.TestCmsSystemInfo"` — run one test case. This is the preferred way to run a single test (the legacy `-PtestCaseToRun=...` property still works but is deprecated and is overridden by `--tests` if both are given). With no filter at all, `testSingle` defaults to running `TestCmsSystemInfo`.
- `./gradlew testGwt` — runs GWT client tests (`org.opencms.client.test.AllTests`), forces Jetty 9.4.x on the classpath for GWT JUnitShell compatibility.

All `Test` tasks share config (`tasks.withType(Test).configureEach`): `useJUnit()` (JUnit 3/4 style, not JUnit 5), `ignoreFailures = true` (the build won't fail on red tests — check output/reports explicitly), and system properties pointing at `test/data`, `webapp/`, and the project root, since many tests spin up a `CmsObject`/`CmsShell` against real VFS test data rather than mocking it.

Test DB: `test/test.properties` selects the backing database for tests (defaults to `hsqldb`; MySQL/Oracle/PostgreSQL/MSSQL/DB2 also supported — see `data/WEB-INF/config.<product>/opencms.properties` for driver config). Update the paths in `test/test.properties` if working outside a checkout at a non-standard location.

## Source layout (non-Maven-standard — this matters)

Gradle source sets are defined explicitly in `build.gradle` and map 1:1 to top-level directories, each compiling to a *separate* configuration/classpath:

| Source set | Dir | Purpose |
|---|---|---|
| `main` | `src/` | Core OpenCms engine (VFS, security, DB layer, JSP/Flex integration, workplace backend, etc.) |
| `modules` | `src-modules/` | Java code that ships as part of specific OpenCms modules |
| `gwt` | `src-gwt/` | GWT client code (ADE/sitemap+container-page editor, UGC forms, widget set) — excludes `**/super_src/**` |
| `setup` | `src-setup/` | The OpenCms setup wizard application |
| `test` | `test/` | Server-side JUnit tests (excludes `data/**`, which is test fixture VFS content, not source) |
| `testGwt` | `test-gwt/` | GWT client-side JUnit tests |

Because these are separate source sets/configurations (`compile`, `modulesCompile`, `gwtCompile`, `setupCompile`, `testCompile`, `testGwtCompile`, each `extendsFrom` the previous appropriately — see `configurations {}` in `build.gradle`), code in `src/` cannot see code in `src-modules/` or `src-gwt/` at compile time, and vice versa isn't fully symmetric either. When adding a class, put it in the source set matching where it's consumed, not just wherever seems convenient.

`modules/` (top level, distinct from `src-modules/`) contains actual **OpenCms modules**: each subdirectory (e.g. `modules/org.opencms.base`, `modules/org.opencms.configuration`, the `modules/org.opencms.locale.*` translations) has a `module.properties`, a `resources/` tree that mirrors the OpenCms VFS (`resources/system/...`), and optionally `resources/manifest.xml` describing module metadata/dependencies. These are packaged into deployable ZIPs by the generated `dist_<moduleName>` Gradle tasks — one is auto-registered per entry in `modules_list` (`build-default.properties`). If a module's `module.properties` sets `workplacelocalization`, a companion `jar_<moduleName>` task also bundles its workplace locale message bundles into a JAR.

`webapp/` is the deployable webapp skeleton: `webapp/WEB-INF/config/opencms*.xml` and `opencms.properties` are the runtime configuration (VFS, sites, modules, search, scheduler, workplace, import/export); `webapp/WEB-INF/classes/` holds `log4j2.xml`, `ehcache.xml`, JPA `persistence.xml`. Editing files under `webapp/WEB-INF/config/` changes the actual runtime configuration that ships and that `test/` points at via `test.webapp.path`.

## Architecture notes

- **Entry point / facade**: `org.opencms.main.OpenCms` is the static system registry (holds the running system's singletons: security manager, module manager, workplace manager, etc.). `org.opencms.main.CmsShell` is the interactive/scriptable console used both for admin scripting and heavily in tests to drive the system without a servlet container.
- **VFS API**: `org.opencms.file.CmsObject` is the primary API surface users of the OpenCms API code against — most operations (read/write resources, permissions, properties, publishing) go through a `CmsObject` bound to a specific user/project/site context. It's large (thousands of lines); grep it rather than reading it end to end.
- **Package map** (`src/org/opencms/*`), useful for orienting a change:
  - `db/` — persistence layer (driver interfaces + SQL implementations for the "vfs/project/user/history/subscription" drivers configured via `drivers_*` properties)
  - `file/`, `security/`, `lock/`, `relations/` — VFS resource model, permissions, locking, resource relations
  - `module/` — module install/export/dependency management (works with the `modules/` directories described above)
  - `workplace/`, `ui/` — legacy JSP-based admin workplace vs. the newer Vaadin-based (`org.opencms.ui`) admin UI
  - `ade/` — the container-page/sitemap editor backend (paired with GWT client code in `src-gwt/org/opencms/ade`)
  - `gwt/` — RPC service endpoints consumed by the GWT client modules
  - `search/` — Solr integration
  - `xml/` — XML content schema handling (structured content types)
  - `staticexport/`, `publish/`, `importexport/` — static HTML export, the online/offline publish workflow, VFS import/export
  - `workflow/`, `scheduler/`, `letsencrypt/`, `webdav/`, `cmis/`, `jlan/` (SMB), `rmi/` — integration/protocol layers
- **GWT/server pairing**: client modules declared in `src-gwt/gwt-modules.properties` (`org.opencms.ade.OpenCms`, `org.opencms.ugc.Ugc`, `org.opencms.ui.WidgetSet`) are compiled by the `gwt_<module>` tasks and correspond to RPC services in `src/org/opencms/gwt/`. `super_src/` directories under `src-gwt` are GWT super-source overrides (excluded from the normal `gwt`/`testGwt` compile, picked up specially by the GWT compiler) — used e.g. under `src-gwt/org/opencms/ui/client/super_src` for Vaadin client-side overrides.
- **Configuration**: system behavior is driven by XML in `webapp/WEB-INF/config/opencms-*.xml`, parsed by handler classes in `src/org/opencms/configuration/`. The `modules/org.opencms.configuration` module ships default versions of notification templates etc. under `resources/system/config/notification/`.

## Code style

Checkstyle config: `Checkstyle_OpenCms.xml` (severity mostly `warning`), suppressions in `CheckStyle_OpenCms_Suppression.xml`, IDE fileset wiring in `.checkstyle`. Notable enforced rules: brace requirements (`NeedBraces`, `LeftCurly`/`RightCurly`), no hidden fields, no redundant/unused imports, `switch` must have `default`, utility classes must hide their constructor, no inner assignments. Match existing code formatting in a file rather than reformatting wholesale.
