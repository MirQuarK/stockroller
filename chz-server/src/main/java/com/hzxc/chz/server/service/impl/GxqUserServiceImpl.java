package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.common.Constant;
import com.hzxc.chz.dao.GxqUserRepository;
import com.hzxc.chz.entity.GxqUser;
import com.hzxc.chz.server.service.GxqUserService;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class GxqUserServiceImpl implements GxqUserService {
    private static Logger logger = LoggerFactory.getLogger(GxqUserServiceImpl.class);
    @Autowired
    GxqUserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DistributionLock distributionLock;

    @Override
    @Cacheable(key = "#prefix + 'USER_INFO_'+#id", value = "USER_INFO_DATA")
    public GxqUser getUserById(String prefix, int id) {
        return userRepository.findById(id);
    }

    @Override
    @Cacheable(key = "#prefix + 'USER_INFO_'+#mobile", value = "USER_INFO_DATA")
    public GxqUser getUserByMobile(String prefix, String mobile) {
        return userRepository.findByMobile(mobile);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#prefix + 'USER_INFO_'+#user.id", value = "USER_INFO_DATA"),
                    @CacheEvict(key = "#prefix + 'USER_INFO_'+#user.mobile", value = "USER_INFO_DATA")
            })
    public void saveUser(String prefix, GxqUser user) {
        boolean isNew = false;
        if (user.getId() == 0) {
            isNew = true;
        }
        userRepository.save(user);
        if (isNew) {
            redisTemplate.opsForValue().set(Constant.KEY_NEW_USER_PREFIX + user.getId(),1,1, TimeUnit.DAYS);
        }
    }

    /**
     * 刷新用户信息
     *
     * @param userId
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(key = "#prefix + 'USER_INFO_'+#user.id", value = "USER_INFO_DATA", condition = "#uesr.id!=null"),
                    @CacheEvict(key = "#prefix + 'USER_INFO_'+#user.mobile", value = "USER_INFO_DATA", condition = "#user.unionId!=null")
            })
    public void flushUser(String prefix, GxqUser userId) {

    }

    @Override
    public int getShortMsgCount(int userId) {
        String key = Constant.getUserShortMsgKey(userId);
        return redisTemplate.opsForList().size(key).intValue();
    }
}
