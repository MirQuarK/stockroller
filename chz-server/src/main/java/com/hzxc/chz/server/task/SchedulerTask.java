package com.hzxc.chz.server.task;

import com.hzxc.chz.common.Constant;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定期执行类
 *
 * @author chz
 * @version create at：2017年3月13日 下午4:13:10
 */
@Component
public class SchedulerTask {
    protected static final Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DistributionLock distributionLock;


    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void delayUpdateLikeAndPlayCount() {
        String lockKey = "delayUpdateLikeAndPlayCount";
        boolean lock = false;
        try {
            lock = distributionLock.tryLock(lockKey, 60);//60s
            if (lock) {
                delayUpdateLikeCount();
                delayUpdatePlayCount();
            }
        } catch (Exception e) {
            logger.error("exec delayUpdateLikeAndPlayCount error:", e);
            e.printStackTrace();
        } finally {
            if (lock) {
                distributionLock.unLock(lockKey);
            }
        }
    }

    private void delayUpdateLikeCount() {

    }


    private void delayUpdatePlayCount() {

    }

    @Scheduled(fixedRate = 20000)
    public void testTasks() {
        // logger.info("每20秒执行一次。开始……");
        // statusTask.healthCheck();
        // logger.info("每20秒执行一次。结束。");
    }
}
