package com.huanfan.shiro;
/**
 * 使用shiro密码限制
 */

import com.huanfan.redis.RedisManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimiyMatcher extends SimpleCredentialsMatcher {

    @Autowired
    RedisManager redisManager;

    private int expire;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String name = (String) token.getPrincipal();
        String key = this.getKey(ShiroConstants.SHIRO_PASSWD_LIMIT, name);
        String limitStr = redisManager.get(key);
        AtomicInteger limit = null;
        if (StringUtils.isBlank(limitStr)) {
            //first matcher
            redisManager.setex(key, String.valueOf(1), expire);
            limit = new AtomicInteger(0);
        } else {
            limit = new AtomicInteger(Integer.valueOf(limitStr));
        }
        if (5 < limit.incrementAndGet()) {
            //to limit
            throw new ExcessiveAttemptsException();
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //if mathes clear limit
            redisManager.del(key);
        } else {
            //else add limit
            redisManager.setex(key, limit.toString(), (int) redisManager.ttl(key));
        }
        return matches;
    }

    @Override
    public String toString() {
        return "加载密码限制";
    }

    private String getKey(String prefix, String key) {
        return prefix + key;
    }

    public RedisManager getRedis() {
        return redisManager;
    }

    public void setRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
