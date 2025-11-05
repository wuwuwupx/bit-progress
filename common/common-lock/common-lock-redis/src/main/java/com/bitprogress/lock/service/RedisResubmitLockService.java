package com.bitprogress.lock.service;

import com.bitprogress.lock.handler.ResubmitLockHandler;
import com.bitprogress.service.RedissonLockService;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class RedisResubmitLockService implements ResubmitLockHandler {

    private final RedissonLockService redissonLockService;

    /**
     * 锁
     *
     * @param lockKey    锁key
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return 是否成功
     */
    @Override
    public boolean lock(String lockKey, long expireTime, TimeUnit timeUnit) {
        return redissonLockService.tryLock(lockKey, 0, expireTime, timeUnit);
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁key
     */
    @Override
    public void unlock(String lockKey) {
        redissonLockService.unlock(lockKey);
    }

}
