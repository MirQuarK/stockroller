package com.hzxc.chz.server.util;

import com.hzxc.chz.service.DistributionLock;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create by chz on 2017/12/6
 */
@Component
public class RedisDistributionLock implements DistributionLock {
    private static final String LOCK_PREFIX = "LOCK_";
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 不阻塞获取锁
     *
     * @param lockKey
     * @param expireLock
     * @return
     */
    @Override
    public boolean tryLock(String lockKey, int expireLock) {
        String lockPk = LOCK_PREFIX.concat(lockKey);
        if (redisTemplate.hasKey(lockPk)) {
            return false;
        }
        String lockValue = System.currentTimeMillis() + RandomStringUtils.randomAscii(5);
        SessionCallback<List<Object>> sessionCallback = new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.watch(lockPk);//监视lockPk，如果被其他连接操作，则接下去对此key的操作将失败
                redisOperations.multi();//开启事务
                redisOperations.opsForValue().setIfAbsent(lockPk, lockValue);
                redisOperations.expire(lockPk, expireLock, TimeUnit.SECONDS);
                List<Object> res = redisOperations.exec();//执行上述2个动作，保证设置完后进行expire，防止死锁
                redisOperations.unwatch();
                return res;
            }
        };

        //因为涉及事务，所以需要在一个redis连接里完成，SessionCallback会在同一个redis连接里完成操作
        List<Object> res = redisTemplate.execute(sessionCallback);
        if(res==null || res.size()==0){
            //setnx和expire没有返回，说明watch后，lockPk被其他连接操作过了，事务执行失败
            return false;
        }
        String getValue = redisTemplate.opsForValue().get(lockPk);
        return lockValue.equals(getValue);
    }

    /**
     * 阻塞方式获取锁
     *
     * @param lockKey
     * @param expireLock
     * @return
     */
    @Override
    public void lock(String lockKey, int expireLock) {
        while(!tryLock(lockKey,expireLock)){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解锁
     *
     * @param lockKey
     */
    @Override
    public void unLock(String lockKey) {
        String lockPk = LOCK_PREFIX.concat(lockKey);
        redisTemplate.delete(lockPk);
    }
}
