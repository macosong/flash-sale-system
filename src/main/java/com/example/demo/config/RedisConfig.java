//package com.example.demo.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//
//public class RedisConfig extends CachingConfigurerSupport {
//    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
//
//    @Autowired
//    private JedisConnectionFactory jedisConnectionFactory;
//
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        //初始化缓存管理器
//        log.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
//                .RedisCacheManagerBuilder
//                .fromConnectionFactory(jedisConnectionFactory);
//        return builder.build();
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
//        //设置序列化
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        // configure redisTemplate
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        //key serialization
//        redisTemplate.setKeySerializer(stringSerializer);
//        //value serialization
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        //Hash key serialization
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        //Hash value serialization
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    @Bean
//    @Override
//    public CacheErrorHandler errorHandler() {
//        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
//        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
//        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
//                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
//                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
//                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException e, Cache cache) {
//                log.error("Redis occur handleCacheClearError：", e);
//            }
//        };
//        return cacheErrorHandler;
//    }
//}
