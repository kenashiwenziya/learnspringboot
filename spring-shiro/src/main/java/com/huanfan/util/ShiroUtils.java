package com.huanfan.util;

import com.huanfan.domain.SysUser;
import com.huanfan.shiro.ShiroConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SysUser getUserEntity() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static int getUserId() {
        return getUserEntity().getId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static void removeSessionAttribute(Object key) {
        getSession().removeAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        Object value = getSessionAttribute(key);
        if (null == value){
            return null;
        }
        String kaptcha = value.toString();
        getSession().removeAttribute(key);
        return kaptcha;
    }

    public static String getCacheSessionKey(String sessionID) {
        return ShiroConstants.SHIRO_SESSION_PREFIX + sessionID;
    }
}
