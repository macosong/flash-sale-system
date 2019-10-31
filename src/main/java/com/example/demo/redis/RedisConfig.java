package com.example.demo.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * RedisConfig
 *
 * @author maco
 * @data 2019/10/29
 */
@Setter
@Getter
@Component
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int poolMaxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int poolMaxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int poolMaxWait;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int poolMinIdle;
    @Value("${spring.redis.timeout}")
    private int timeout;
}
