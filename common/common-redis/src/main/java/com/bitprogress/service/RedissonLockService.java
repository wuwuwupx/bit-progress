package com.bitprogress.service;

import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 基于String操作
 */
@Component
@AllArgsConstructor
public class RedissonLockService {

    private final RedissonClient redissonClient;

    /**
     * 统计redis key的数量
     *
     * @return redis key count
     */
    public Long count() {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.count();
    }

    /**
     * 统计redis key的数量
     *
     * @return redis key count
     */
    public Long countKeys(Collection<String> keys) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.countExists(CollectionUtils.toArray(keys));
    }

    /**
     * 统计redis key的数量
     *
     * @return redis key count
     */
    public Long countKeys(String... keys) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.countExists(keys);
    }

    /**
     * 检查redis key 是否存在
     *
     * @param key redis key
     * @return true：key存在，false：key不存在
     */
    public Boolean hasKey(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.countExists(key) > 0;
    }

    /**
     * 获取redis key 对应的数据类型
     *
     * @param key redis key
     * @return redis key 对应的数据类型
     */
    public RType getType(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.getType(key);
    }

    /**
     * 获取redis的所有key
     *
     * @return redis all key
     */
    public Iterable<String> getKeys() {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.getKeys();
    }

    /**
     * 获取redis key的剩余过期时间
     * 指定时间类型
     * -1 redis key 为设置过期策略
     * -2 redis key 不存在
     *
     * @param key      redis key
     * @param timeUnit 时间类型
     * @return redis剩余过期时长（毫秒）
     */
    public Long getExpireTime(String key, TimeUnit timeUnit) {
        Long time = getExpireTime(key);
        return time == -1 || time == -2 ? time : timeUnit.convert(time, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取redis key的剩余过期时间
     * -1 redis key 为设置过期策略
     * -2 redis key 不存在
     *
     * @param key redis key
     * @return redis剩余过期时长（毫秒）
     */
    public Long getExpireTime(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.remainTimeToLive(key);
    }

    /**
     * 设置redis key的过期时间
     *
     * @param key      redis key
     * @param time     过期时长
     * @param timeUnit 时间类型
     * @return true：设置成功，false：设置失败
     */
    public Boolean expire(String key, Long time, TimeUnit timeUnit) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.expire(key, time, timeUnit);
    }

    /**
     * 设置redis key的过期时间点
     *
     * @param key       redis key
     * @param timestamp 过期时间节点时间戳
     * @return true：设置成功，false：设置失败
     */
    public Boolean expireAt(String key, Long timestamp) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.expireAt(key, timestamp);
    }

    /**
     * 清除redis key的过期时间设置
     *
     * @param key redis key
     * @return true：设置成功，false：设置失败
     */
    public Boolean clearExpire(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.clearExpire(key);
    }

    /**
     * 获取value数据类型的数据
     *
     * @param key redis key
     * @return redis value
     */
    public String getForValue(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 设置value数据类型的数据
     *
     * @param key   redis key
     * @param value 设置的值
     */
    public void setForValue(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 设置带过期时间的value数据类型的数据
     * 默认单位为秒
     *
     * @param key   redis key
     * @param value 设置的值
     */
    public void setExpireForValue(String key, String value, Long time) {
        setExpireForValue(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置带过期时间的value数据类型的数据
     *
     * @param key   redis key
     * @param value 设置的值
     */
    public void setExpireForValue(String key, String value, Long time, TimeUnit timeUnit) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value, time, timeUnit);
    }

}
