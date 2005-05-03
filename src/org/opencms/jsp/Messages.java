/*
 * File   : $Source: /alkacon/cvs/opencms/src/org/opencms/jsp/Messages.java,v $
 * Date   : $Date: 2005/05/03 15:56:51 $
 * Version: $Revision: 1.7 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2002 - 2005 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.jsp;

import org.opencms.file.CmsObject;
import org.opencms.file.CmsRequestContext;
import org.opencms.flex.CmsFlexController;
import org.opencms.i18n.A_CmsMessageBundle;
import org.opencms.i18n.CmsMessageContainer;
import org.opencms.i18n.I_CmsMessageBundle;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * Convenience class to access the localized messages of this OpenCms package.<p> 
 * 
 * 
 * Additionally two utility methods for localization in the context of {@link javax.servlet.jsp.tagext.TagSupport} 
 * implementations of the <code>org.opencms.jsp</code> package are provided .<p>
 * 
 * The <code>javax.servlet.jsp.tagext.TagSupport</code> API constraint only allows 
 * to throw certain <code>Exception</code> types which forbids to use {@link org.opencms.main.CmsException} 
 * which will be localized with the current user's locale at the time the request is evaluated. <p>
 * 
 * At the same time <code>TagSupport</code> implementations may use their member <code>pageContext</code> 
 * to get the <code>CmsObject</code> and therefore the user request's locale. <p>
 * 
 * These methods provided here factor out the localization of exception messages and return Strings 
 * for the <code>org.opencms.jsp</code> pacakge. <p>
 * 
 * @author Jan Baudisch (j.baudisch@alkacon.com)
 * @since 5.7.3
 */
public final class Messages extends A_CmsMessageBundle {

    /** Message constant for key in the resource bundle. */
    public static final String ERR_COLLECTOR_NOT_FOUND_1 = "ERR_COLLECTOR_NOT_FOUND_1";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_MISSING_CMS_CONTROLLER_1 = "ERR_MISSING_CMS_CONTROLLER_1";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_RUNTIME_1 = "ERR_RUNTIME_1";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTCHECK_WRONG_PARENT_0 = "ERR_TAG_CONTENTCHECK_WRONG_PARENT_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTINFO_WRONG_PARENT_0 = "ERR_TAG_CONTENTINFO_WRONG_PARENT_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTLOAD_INDEX_SIZE_0 = "ERR_TAG_CONTENTLOAD_INDEX_SIZE_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTLOAD_MISSING_COLLECTOR_0 = "ERR_TAG_CONTENTLOAD_MISSING_COLLECTOR_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTLOAD_MISSING_PARAM_0 = "ERR_TAG_CONTENTLOAD_MISSING_PARAM_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTLOOP_WRONG_PARENT_0 = "ERR_TAG_CONTENTLOOP_WRONG_PARENT_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_CONTENTSHOW_WRONG_PARENT_0 = "ERR_TAG_CONTENTSHOW_WRONG_PARENT_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_INFO_0 = "ERR_TAG_INFO_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_LABEL_0 = "ERR_TAG_LABEL_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_LINK_0 = "ERR_TAG_LINK_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_PARAM_ORPHAN_0 = "ERR_TAG_PARAM_ORPHAN_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_PROPERTY_0 = "ERR_TAG_PROPERTY_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_TAG_USER_0 = "ERR_TAG_USER_0";

    /** Message constant for key in the resource bundle. */
    public static final String ERR_XML_DOCUMENT_UNMARSHAL_1 = "ERR_XML_DOCUMENT_UNMARSHAL_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_ACTIONELEM_NOT_INIT_0 = "GUI_ERR_ACTIONELEM_NOT_INIT_0";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_FILE_PROP_MISSING_2 = "GUI_ERR_FILE_PROP_MISSING_2";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_GEN_LINK_1 = "GUI_ERR_GEN_LINK_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_INFO_PROP_READ_1 = "GUI_ERR_INFO_PROP_READ_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_INVALID_INFO_PROP_0 = "GUI_ERR_INVALID_INFO_PROP_0";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_INVALID_INFO_PROP_1 = "GUI_ERR_INVALID_INFO_PROP_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_INVALID_USER_PROP_1 = "GUI_ERR_INVALID_USER_PROP_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_USER_PROP_READ_1 = "GUI_ERR_USER_PROP_READ_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_ERR_WORKPL_LABEL_READ_1 = "GUI_ERR_WORKPL_LABEL_READ_1";

    /** Message constant for key in the resource bundle. */
    public static final String GUI_TAG_USER_ADDITIONALINFO_0 = "GUI_TAG_USER_ADDITIONALINFO_0";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_DEBUG_INTERRUPTED_EXCEPTION_1 = "LOG_DEBUG_INTERRUPTED_EXCEPTION_1";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_ERR_CONTENT_SHOW_1 = "LOG_ERR_CONTENT_SHOW_1";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_ERR_JSP_BEAN_0 = "LOG_ERR_JSP_BEAN_0";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_ERR_JSP_WRITE_0 = "LOG_ERR_JSP_WRITE_0";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_ERR_PROCESS_CONTENTINFO_0 = "LOG_ERR_PROCESS_CONTENTINFO_0";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_LOGIN_FAILED_2 = "LOG_LOGIN_FAILED_2";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_LOGIN_SUCCESSFUL_2 = "LOG_LOGIN_SUCCESSFUL_2";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_LOGOUT_FAILED_2 = "LOG_LOGOUT_FAILED_2";

    /** Message constant for key in the resource bundle. */
    public static final String LOG_LOGOUT_SUCCESFUL_2 = "LOG_LOGOUT_SUCCESFUL_2";

    /** Name of the used resource bundle. */
    private static final String BUNDLE_NAME = "org.opencms.jsp.messages";

    /** Static instance member. */
    private static final I_CmsMessageBundle INSTANCE = new Messages();

    /**
     * Hides the public constructor for this utility class.<p>
     */
    private Messages() {

        // hide the constructor
    }

    /**
     * Returns an instance of this localized message accessor.<p>
     * 
     * @return an instance of this localized message accessor
     */
    public static I_CmsMessageBundle get() {

        return INSTANCE;
    }

    /**
     * Returns the bundle name for this OpenCms package.<p>
     * 
     * @return the bundle name for this OpenCms package
     */
    public String getBundleName() {

        return BUNDLE_NAME;
    }

    /**
     * Returns the String for the given CmsMessageContainer localized to the 
     * current user's locale if available or to the default locale else. <p>
     * 
     * This method is needed for localization of non- {@link org.opencms.main.CmsException} instances 
     * that have to be thrown here due to API constraints (javax.servlet.jsp) and returned error strings. <p>
     * 
     * @param container A CmsMessageContainer containing the message to localize. 
     * @param context The page context that is known to any calling {@link javax.servlet.jsp.tagext.TagSupport} instance (member <code>pageContext</code>). 
     * @return the String for the given CmsMessageContainer localized to the 
     *         current user's locale if available or to the default locale else. <p>
     */
    public final static String getLocalizedMessage(CmsMessageContainer container, PageContext context) {
    
        return Messages.getLocalizedMessage(container, context.getRequest());
    }

    /**
     * Returns the String for the given CmsMessageContainer localized to the 
     * current user's locale if available or to the default locale else. <p>
     * 
     * This method allows a static method ({@link CmsJspTagInfo#infoTagAction(String, javax.servlet.http.HttpServletRequest)}) 
     * that has no <code>pageContext</code> in scope to lookup the locale at request time. <p>
     *  
     * @see #getLocalizedMessage(CmsMessageContainer, PageContext)
     * @param container A CmsMessageContainer containing the message to localize. 
     * @param request The current request. 
     * @return the String for the given CmsMessageContainer localized to the 
     *         current user's locale if available or to the default locale else. <p>
     */
    public final static String getLocalizedMessage(CmsMessageContainer container, ServletRequest request) {
    
        String msg;
        CmsRequestContext requestContext;
        Locale locale;
        CmsObject cms = CmsFlexController.getCmsObject(request);
        if ((cms == null)
            || ((requestContext = cms.getRequestContext()) == null)
            || ((locale = requestContext.getLocale()) == null)) {
            msg = container.key();
        } else {
            msg = container.key(locale);
        }
        return msg;
    }
}
