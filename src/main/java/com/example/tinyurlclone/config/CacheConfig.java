package com.example.tinyurlclone.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig extends JCacheConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheEventListener<Object, Object> cacheEventLogger() {
        return new CacheEventListener<Object, Object>() {
            @Override
            public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
                logger.info("Cache event = {}, Key = {}, Old value = {}, New value = {}",
                        cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
            }
        };
    }
}
