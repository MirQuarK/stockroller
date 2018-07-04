package com.hzxc.chz.service;

/**
 * create by chz on 2017/12/6
 */
public interface DistributionLock {
    /**
     * 不阻塞获取锁
     * @param lockKey
     * @param expireLock
     * @return
     */
    boolean tryLock(String lockKey,int expireLock);

    /**
     * 阻塞方式获取锁
     * @param lockKey
     * @param expireLock
     * @return
     */
    void lock(String lockKey,int expireLock);

    /**
     * 解锁
     * @param lockKey
     */
    void unLock(String lockKey);
}
