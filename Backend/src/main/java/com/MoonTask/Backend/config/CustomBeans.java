package com.MoonTask.Backend.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class CustomBeans {

    /**
     * This bean create a redis template for storing data in in-memory.
     * This method provide {@link RedisTemplate} bean.
     * <p>
     *     Here we set the key and value serializer for redis template.
     *     We use StringRedisSerializer for key and GenericJacksonJsonRedisSerializer for value.
     *     We use ObjectMapper for converting Java objects to JSON and vice versa.
     * </p>*/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        // Key serializers
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        //value serializers
        ObjectMapper objectMapper = new ObjectMapper();
        GenericJacksonJsonRedisSerializer serializer = new GenericJacksonJsonRedisSerializer(objectMapper);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * This method provide {@link CacheManager} bean.
     * <p>
     *     1.{@link ObjectMapper} converts java object into JSON format.
     *     2.Time to live: data storing time(here after 30 min data will be deleted).
     *     3.Key serialization: The keys will be saved as plain text.
     *     4.Value serialization: defines how actual objects will be saved.
     * </p>
     * @param connectionFactory a plug that is connects to actual Redis server.*/
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper obj = new ObjectMapper();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(30, ChronoUnit.MINUTES)) //defines duration
                .disableCachingNullValues() //tells not to cache a result if it is null.
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJacksonJsonRedisSerializer(obj)));

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
    }
}
