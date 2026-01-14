package ru.kliuevia.springapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues();
    }

    /**
     * Для задания разных параметров для разных кешей
     * @param factory
     * @return
     */
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
//        Map<String, RedisCacheConfiguration> configs = new HashMap<>();
//
//        configs.put("users", RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(30))
//                .disableCachingNullValues());
//
//        configs.put("roles", RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(30))
//                .disableCachingNullValues());
//
//        return RedisCacheManager.builder(factory)
//                .withInitialCacheConfigurations(configs)
//                .build();
//    }
}
