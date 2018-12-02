package com.lihebin.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //按cacheName设置spring-cache-redis缓存时间
        Map<String, Long> expires = new HashMap<>();
        expires.put("tenMinutes", (long) (60));
        expires.put("oneHour", (long) (60*60));
        expires.put("oneDay", (long) (60*60*24));
        expires.put("permanent", (long) 0);
        cacheManager.setExpires(expires);
        return cacheManager;
    }
}
