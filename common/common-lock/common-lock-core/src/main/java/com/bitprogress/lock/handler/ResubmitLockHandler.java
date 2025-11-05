package com.bitprogress.lock.handler;

import java.util.concurrent.TimeUnit;

public interface ResubmitLockHandler {

    /**
     * 锁
     *
     * @param lockKey    锁key
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return 是否成功
     */
    boolean lock(String lockKey, long expireTime, TimeUnit timeUnit);

    /**
     * 释放锁
     *
     * @param lockKey 锁key
     */
    void unlock(String lockKey);

}
