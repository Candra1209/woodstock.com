package com.woodstock.app.config.exception_handler;

import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class MyRedisCacheManagerConfiguration {

    @Bean
    public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer(){

        return builder -> builder
                .withCacheConfiguration("treeType", RedisCacheConfiguration
                        .defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                        .entryTtl(Duration.ofMinutes(5)));
    }

}
