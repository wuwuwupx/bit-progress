package com.bitprogress.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author wuwuwupx
 */
@Component
public class RedisLockUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认超时时间
     */
    private static final long DEFAULT_EXPIRE = 60L;

    /**
     * 默认超时时间单位为秒
     */
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 加锁，返回true表示加锁成功
     * 默认加锁时间为60s
     *
     * @param key
     * @param value
     */
    public boolean lock(String key, String value) {
        return lock(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 加锁，返回true表示加锁成功
     * 默认加锁时间单位为秒
     *
     * @param key
     * @param value
     * @param expire
     */
    public boolean lock(String key, String value, long expire) {
        return lock(key, value, expire, DEFAULT_TIME_UNIT);
    }

    /**
     * 加锁
     *
     * @param key
     * @param value
     * @param expire 单位为秒
     * @return
     */
    public boolean lock(String key, String value, long expire, TimeUnit timeUnit) {
        Expiration expiration = Expiration.from(expire, timeUnit);
        try {
            Boolean execute = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                        connection.set(key.getBytes(), value.getBytes(), expiration,
                                RedisStringCommands.SetOption.ifAbsent()));
            return Boolean.TRUE.equals(execute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解锁，删除key
     *
     * @param key
     * @param requestId
     */
    public void unlock(String key, String requestId) {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setResultType(Boolean.class);
        script.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end");
        stringRedisTemplate.execute(script, Collections.singletonList(key), requestId);
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
