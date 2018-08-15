package com.hzxc.chz.server.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis缓存配置类
 *
 * @author chz
 * @version create at：2017年3月13日 下午7:18:44
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * CacheManager
     *
     * @param redisTemplate
     * @return RedisCacheManager
     */
    @Bean("redisCacheManager")
    @Primary
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cm = new RedisCacheManager(redisTemplate);
        cm.setDefaultExpiration(24L * 60 * 60);//单位秒
        return cm;
    }


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        // StringRedisTemplate template = new StringRedisTemplate(factory);
        // Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
        //         Object.class);
        // ObjectMapper om = new ObjectMapper();
        // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // jackson2JsonRedisSerializer.setObjectMapper(om);
        // template.setValueSerializer(jackson2JsonRedisSerializer);
        // template.afterPropertiesSet();
        // return template;
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        GenericFastJsonRedisSerializer serializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    // 获取session信息需要的redis配置。
    @Bean()
    public RedisTemplate redisTemplateCookiet(RedisConnectionFactory factory) {

        // RedisOperationsSessionRepository 里的配置。
        {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(factory);
            JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
            redisTemplate.setDefaultSerializer(serializer);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            return redisTemplate;
        }
    }
}
