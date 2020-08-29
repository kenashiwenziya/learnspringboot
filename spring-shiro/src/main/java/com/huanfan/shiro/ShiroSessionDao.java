package com.huanfan.shiro;

import com.huanfan.domain.SysUser;
import com.huanfan.redis.RedisManager;
import com.huanfan.util.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ShiroSessionDao extends AbstractSessionDAO {

    private int expire;

    @Autowired
    private RedisManager redisManager;

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.error("session id is null");
            return null;
        }
        Session session = null;
        try {
            byte[] data = redisManager.get(this.getKey(ShiroConstants.SHIRO_SESSION_PREFIX, sessionId.toString()).getBytes());
            session = (Session) SerializationUtils.deserialize(data);
        } catch (Exception e) {
            log.error("读取Session失败：{}", sessionId.toString());
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    int i = 0;

    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        session.setTimeout(expire);
        int timeout = expire / 1000;
        String userId = getUserId(session);
        redisManager.setex(this.getKey(ShiroConstants.SHIRO_SESSION_PREFIX, session.getId().toString()).getBytes(), SerializationUtils.serialize(session), timeout);
    }


    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        log.debug("删除session:{}", session.getId());
        redisManager.del(this.getKey(ShiroConstants.SHIRO_SESSION_PREFIX, session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<String> keys = redisManager.keys(ShiroConstants.SHIRO_SESSION_ID_PREFIX + "*");
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Session s = null;
                try {
                    s = (Session) SerializationUtils.deserialize(redisManager.get(key.getBytes()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sessions.add(s);
            }
        }
        return sessions;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    private String getKey(String prefix, String key) {
        return prefix + key;
    }

    public String getUserId(Session session) {
        SimplePrincipalCollection pricipal = (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        if (null != pricipal) {
            return String.valueOf(((SysUser) pricipal.getPrimaryPrincipal()).getId());
        }
        return null;
    }
}
