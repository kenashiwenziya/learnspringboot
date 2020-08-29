package com.huanfan.shiro;

import com.huanfan.domain.SysUser;
import com.huanfan.redis.RedisManager;
import com.huanfan.util.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class KickoutSessionFilter extends AccessControlFilter {

    @Autowired
    private RedisManager redisManager;

    private SessionManager sessionManager;


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 后一个把前一个强制踢出去
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = ShiroUtils.getSubject();
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }
        Session session = subject.getSession();
        int userId = ((SysUser) subject.getPrincipal()).getId();
        String key = this.getKey(ShiroConstants.USER_ONLINE, userId);
        String onlineSessionID = redisManager.get(key);
        if (StringUtils.isBlank(onlineSessionID)) {
            // bug修复，getTimeout返回的是毫秒数，入参数是秒
            redisManager.setex(key, session.getId().toString(), (int) (session.getTimeout()/1000));
        } else {
            // 已经在线的的onlineSessionID可能被其他浏览器登录时，寻找不到，抛出异常， kickSession并不是null这么简单
            Session kickSession = sessionManager.getSession(new DefaultSessionKey(onlineSessionID));
            if (null != kickSession && !StringUtils.equals(kickSession.getId().toString(), session.getId().toString())) {
                // bug修复 同一用户，不同的session 删除旧的session
                redisManager.del(ShiroConstants.SHIRO_SESSION_PREFIX + kickSession.getId().toString());
                redisManager.setex(key, session.getId().toString(), (int) (session.getTimeout()/1000));
            }else{
                // 不同用户
            }
        }
        return true;
    }

    private String getKey(String prefix, int key) {
        return prefix + key;
    }

    public RedisManager getRedis() {
        return redisManager;
    }

    public void setRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
