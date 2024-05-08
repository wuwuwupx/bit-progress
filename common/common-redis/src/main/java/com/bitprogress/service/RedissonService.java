package com.bitprogress.service;

import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于String操作
 */
@Component
@AllArgsConstructor
public class RedissonService {

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
     * 检查redis key 是否存在
     *
     * @param key redis key
     * @return true：key存在，false：key不存在
     */
    public Boolean hasKey(String key) {
        return countKeys(key) > 0;
    }

    /**
     * 统计redis key的数量
     *
     * @return redis key count
     */
    public Long countKeys(Collection<String> keys) {
        return countKeys(CollectionUtils.toArray(keys));
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
     * redis key 重命名
     *
     * @param key    redis key
     * @param newKey 新名称
     */
    public void rename(String key, String newKey) {
        RKeys rKeys = redissonClient.getKeys();
        rKeys.rename(key, newKey);
    }

    /**
     * 删除redis key
     *
     * @param key redis
     * @return true：删除成功，false：删除失败
     */
    public Boolean delete(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.delete(key) == 1;
    }

    /**
     * 删除 redis key
     *
     * @param keys redis key 集合
     * @return 删除的redis key 数量
     */
    public Long delete(Collection<String> keys) {
        return delete(CollectionUtils.toArray(keys));
    }

    /**
     * 删除 redis key
     *
     * @param keys redis key 数组
     * @return 删除的redis key 数量
     */
    public Long delete(String... keys) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.delete(keys);
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
     * 获取 Bucket 类型的操作接口
     *
     * @param key redis key
     * @return Bucket 类型的操作接口
     */
    private RBucket<String> getBucket(String key) {
        return redissonClient.getBucket(key);
    }

    /**
     * 获取value数据类型的数据
     *
     * @param key redis key
     * @return redis value
     */
    public String getFromValue(String key) {
        RBucket<String> bucket = getBucket(key);
        return bucket.get();
    }

    /**
     * 设置value数据类型的数据
     *
     * @param key   redis key
     * @param value 设置的值
     */
    public void setFromValue(String key, String value) {
        RBucket<String> bucket = getBucket(key);
        bucket.set(value);
    }

    /**
     * 设置带过期时间的value数据类型的数据
     * 默认单位为秒
     *
     * @param key   redis key
     * @param value 设置的值
     */
    public void setExpireFromValue(String key, String value, Long time) {
        setExpireFromValue(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置带过期时间的value数据类型的数据
     *
     * @param key   redis key
     * @param value 设置的值
     */
    public void setExpireFromValue(String key, String value, Long time, TimeUnit timeUnit) {
        RBucket<String> bucket = getBucket(key);
        bucket.set(value, time, timeUnit);
    }

    /**
     * 获取 hashMap类型的操作接口
     *
     * @param key redis key
     * @return hashMap类型的操作接口
     */
    private RMap<String, String> getMap(String key) {
        return redissonClient.getMap(key);
    }

    /**
     * 获取hashMap value
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @return hashMap value
     */
    public String getFromMap(String key, String hashKey) {
        RMap<String, String> rMap = getMap(key);
        return rMap.get(hashKey);
    }

    /**
     * 获取hashMap k-v 列表
     *
     * @param key      redis key
     * @param hashKeys hashMap key 集合
     * @return hashMap k-v 列表
     */
    public Map<String, String> getAllFromMap(String key, Set<String> hashKeys) {
        RMap<String, String> rMap = getMap(key);
        return rMap.getAll(hashKeys);
    }

    /**
     * 设置 hashMap类型的值
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @param value   hashMap value
     * @return hashMap key 原本设置的值
     */
    public String putFromMap(String key, String hashKey, String value) {
        RMap<String, String> rMap = getMap(key);
        return rMap.put(hashKey, value);
    }

    /**
     * 自增 hashMap类型的值
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @return 自增后的值
     */
    public Number incrementFromMap(String key, String hashKey) {
        return addFromMap(key, hashKey, 1);
    }

    /**
     * 累加 hashMap类型的值
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @param value   hashMap value
     * @return 累加后的值
     */
    public Number addFromMap(String key, String hashKey, Number value) {
        RMap<String, Number> rMap = redissonClient.getMap(key);
        return rMap.addAndGet(hashKey, value);
    }

    /**
     * 设置 hashMap类型的值
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @param value   hashMap value
     * @return true：hashKey原本不存在，false：hashKey原本存在现已被更新
     */
    public Boolean fastPutFromMap(String key, String hashKey, String value) {
        RMap<String, String> rMap = getMap(key);
        return rMap.fastPut(hashKey, value);
    }

    /**
     * 删除 hashMap里的值
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @return 被删除的值
     */
    public String deleteFromMap(String key, String hashKey) {
        RMap<String, String> rMap = getMap(key);
        return rMap.remove(hashKey);
    }

    /**
     * 当 hash key 的值等于 value 的时候，从hashMap中删除
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @param value   hashMap value
     * @return true：删除成功，false：删除失败
     */
    public Boolean deleteFromMap(String key, String hashKey, String value) {
        RMap<String, String> rMap = getMap(key);
        return rMap.remove(hashKey, value);
    }

    /**
     * 检查redis hashMap中是否包含对应的keu
     *
     * @param key     redis key
     * @param hashKey hashMap key
     * @return true：包含，false：不包含
     */
    public Boolean containsFromMap(String key, String hashKey) {
        RMap<String, String> rMap = getMap(key);
        return rMap.containsKey(hashKey);
    }

    /**
     * 获取 redis hashMap的所有key
     *
     * @param key redis key
     * @return hashMap的所有key
     */
    public Set<String> keySetFromMap(String key) {
        RMap<String, String> rMap = getMap(key);
        return rMap.keySet();
    }

    /**
     * 模糊 redis hashMap的key
     * h?llo subscribes to hello, hallo and hxllo
     * h*llo subscribes to hllo and heeeello
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param key redis key
     * @return hashMap的所有key
     */
    public Set<String> keySetFromMap(String key, String keyPattern) {
        RMap<String, String> rMap = getMap(key);
        return rMap.keySet(keyPattern);
    }

}
