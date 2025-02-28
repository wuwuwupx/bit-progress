package com.bitprogress.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class RedissonLockService {

    private final RedissonClient redissonClient;

    /**
     * 获取锁
     *
     * @param lockName 锁名称
     * @return 锁
     */
    public RLock getLock(String lockName) {
        return redissonClient.getLock(lockName);
    }

    /**
     * 判断锁是否存在
     *
     * @param lockName 锁名称
     * @return 是否存在
     */
    public boolean isLock(String lockName) {
        return redissonClient
                .getLock(lockName)
                .isLocked();
    }

    /**
     * 加锁
     * 锁不会定时释放
     *
     * @param lockName 锁名称
     * @return 是否加锁成功
     */
    public boolean tryLock(String lockName) {
        return redissonClient
                .getLock(lockName)
                .tryLock();
    }

    /**
     * 加锁，单位默认秒
     * 锁不会定时释放
     *
     * @param lockName 锁名称
     * @param waitTime 等待时间
     * @return 是否加锁成功
     */
    public boolean tryLock(String lockName, long waitTime) {
        return tryLock(lockName, waitTime, -1, TimeUnit.SECONDS);
    }

    /**
     * 加锁，单位默认秒
     *
     * @param lockName  锁名称
     * @param waitTime  等待时间
     * @param leaseTime 锁过期时间
     * @return 是否加锁成功
     */
    public boolean tryLock(String lockName, long waitTime, long leaseTime) {
        return tryLock(lockName, waitTime, leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     *
     * @param lockName  锁名称
     * @param waitTime  等待时间
     * @param leaseTime 锁过期时间
     * @param timeUnit  时间单位
     * @return 是否加锁成功
     */
    public boolean tryLock(String lockName, long waitTime, long leaseTime, TimeUnit timeUnit) {
        try {
            return redissonClient
                    .getLock(lockName)
                    .tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("redisson lock error", e);
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param lockName 锁名称
     */
    public void unlock(String lockName) {
        redissonClient
                .getLock(lockName)
                .unlock();
    }

}
