# Notas para actualizar la localizacion espanola de OpenCms

Fecha de analisis: 2026-07-29

Objetivo: revisar y completar las claves de localizacion en espanol para preparar un PR contra el repositorio de Alkacon. Alkacon mantiene ingles y aleman; el trabajo debe centrarse en `org.opencms.locale.es`.

## Contexto del repo

- Rama local revisada: `rg/master-Locales`.
- Remotos detectados:
  - `origin`: `git@github.com:rgaviras/opencms-core.git`
  - `upstream`: `https://github.com/alkacon/opencms-core.git`
- Estado antes de crear este documento: worktree limpio.
- No se han modificado ficheros de traduccion durante el analisis.

## Donde viven las traducciones

El modulo espanol esta en:

```text
modules/org.opencms.locale.es/
```

`modules/org.opencms.locale.es/module.properties` contiene:

```properties
workplacelocalization=es
```

El build de modulos detecta `workplacelocalization` y crea el JAR de localizacion copiando:

```text
modules/org.opencms.locale.es/resources/system/workplace/locales/es/messages
```

El `manifest.xml` del modulo tambien exporta:

```xml
<exportpoint uri="/system/workplace/locales/es/messages/" destination="WEB-INF/classes/"/>
```

Consecuencia: para claves nuevas en ficheros existentes basta con editar el `.properties`; para ficheros nuevos hay que crear el fichero y actualizar `modules/org.opencms.locale.es/resources/manifest.xml` para mantener coherente el modulo importable.

## Regla de correspondencia de bundles

Los bundles base en ingles estan repartidos principalmente en:

```text
src/
src-modules/
src-setup/
src-gwt/
```

La traduccion espanola debe replicar la ruta relativa bajo:

```text
modules/org.opencms.locale.es/resources/system/workplace/locales/es/messages/
```

Ejemplo:

```text
src/org/opencms/main/messages.properties
modules/org.opencms.locale.es/resources/system/workplace/locales/es/messages/org/opencms/main/messages_es.properties
```

Regla general:

- `messages.properties` -> `messages_es.properties`
- `clientmessages.properties` -> `clientmessages_es.properties`
- `workplace.properties` -> `workplace_es.properties`
- `*_messages.properties` -> `*_messages_es.properties`

Excepciones existentes:

- `htmlmsg.properties` se mantiene sin sufijo.
- `errorpage.properties` se mantiene sin sufijo.

## Codificacion y formato

- Los ficheros espanoles actuales contienen UTF-8 directo con acentos.
- Se probo localmente que `PropertyResourceBundle(InputStream)` carga correctamente UTF-8 con el JDK del repo.
- No convertir a escapes `\uXXXX` de forma masiva.
- Mantener escapes existentes cuando tengan significado tecnico, por ejemplo `\u0020` para espacios significativos.
- Preservar placeholders `{0}`, `{1}`, etc.
- Preservar sufijos de clave como `_0`, `_1`, `_2`; suelen indicar numero de parametros.
- Preservar HTML, `\n`, barras de continuacion `\` y comillas en valores multilinea.
- No ordenar ni reformatear ficheros completos si no es necesario; minimizar el diff para facilitar el PR upstream.

## Estado de cobertura detectado

Resumen aproximado del analisis:

- Bundles base esperados: 124.
- Ficheros espanoles existentes: 131.
- Ficheros esperados en espanol que faltan: 9.
- Claves faltantes dentro de ficheros espanoles ya existentes: 487.
- Brecha total estimada: 546 claves, incluyendo los ficheros nuevos.

Ficheros espanoles completos que faltan:

```text
org/opencms/db/storage/messages_es.properties                                      32 claves
org/opencms/xml/adeconfig/bundledescriptor_messages_es.properties                  8 claves
org/opencms/xml/adeconfig/xmlvfsbundle_messages_es.properties                      6 claves
org/opencms/ai/messages_es.properties                                              5 claves
org/opencms/ade/contenteditor/client/messages_es.properties                        5 claves
org/opencms/search/extractors/messages_es.properties                               1 clave
org/opencms/jsp/search/result/messages_es.properties                               1 clave
org/opencms/jsp/search/config/parser/simplesearch/messages_es.properties           1 clave
org/opencms/xml/templatemapper/messages_es.properties                              0 claves
```

Ficheros existentes con mas claves faltantes:

```text
org/opencms/ui/apps/messages_es.properties                         57
org/opencms/xml/containerpage/messages_es.properties                52
org/opencms/jsp/search/messages_es.properties                       39
org/opencms/search/messages_es.properties                           38
org/opencms/search/solr/messages_es.properties                      31
org/opencms/ui/dialogs/messages_es.properties                       23
org/opencms/jsp/search/config/parser/messages_es.properties         18
org/opencms/repository/messages_es.properties                       17
org/opencms/ade/contenteditor/clientmessages_es.properties          17
org/opencms/loader/messages_es.properties                           13
org/opencms/staticexport/messages_es.properties                     13
org/opencms/configuration/messages_es.properties                    12
org/opencms/ade/postupload/clientmessages_es.properties             12
org/opencms/ui/editors/messagebundle/messages_es.properties         11
org/opencms/main/messages_es.properties                             11
org/opencms/db/generic/messages_es.properties                       10
```

Tambien se detectaron claves con valor exactamente igual al ingles en ficheros espanoles. No todas son errores, porque algunas son tecnicas o nombres propios, pero conviene revisarlas como tercera fase. Top de sospechosos:

```text
org/opencms/workplace/commons/messages_es.properties                198 coincidencias exactas
org/opencms/workplace/tools/accounts/messages_es.properties          44
org/opencms/gwt/clientmessages_es.properties                         39
org/opencms/workplace/tools/modules/messages_es.properties           18
org/opencms/ade/sitemap/clientmessages_es.properties                 16
org/opencms/workplace/tools/searchindex/messages_es.properties       14
org/opencms/xml/containerpage/messages_es.properties                 12
org/opencms/ui/apps/messages_es.properties                           10
```

## Estrategia recomendada

1. Generar una lista reproducible de diferencias contra los bundles base ingleses.
2. Crear primero los 9 ficheros espanoles faltantes y actualizar `manifest.xml`.
3. Completar claves faltantes por paquetes funcionales, empezando por:
   - `org/opencms/ui/apps`
   - `org/opencms/xml/containerpage`
   - `org/opencms/search`
   - `org/opencms/ade/contenteditor`
4. Revisar claves espanolas que siguen iguales al ingles.
5. Validar placeholders, formato `.properties` y carga de bundles.
6. Preparar PR contra `upstream` tocando solo `modules/org.opencms.locale.es`.

## Validaciones sugeridas

Antes del PR:

- Comprobar que no quedan claves base sin equivalente espanol, salvo excepciones justificadas.
- Comprobar que cada valor conserva el mismo conjunto de placeholders que el ingles.
- Comprobar que no se han roto continuaciones multilinea.
- Construir el JAR del modulo espanol con Gradle.
- Si se han agregado ficheros, comprobar que aparecen en `manifest.xml`.
- Revisar el diff para evitar cambios de formato masivos.

## Estimacion

Estimacion pragmatica para completar las 546 claves:

- Traduccion inicial y revision tecnica de placeholders: 1 jornada.
- Revision linguistica real en contexto: 1-2 jornadas.
- Ficheros nuevos, `manifest.xml` y validaciones: media jornada.
- Preparacion del PR: 1-2 horas.

Total recomendado: 2-3 dias de trabajo efectivo. Un primer PR parcial y util podria salir en 1 dia si se limita a ficheros faltantes y paquetes prioritarios.

