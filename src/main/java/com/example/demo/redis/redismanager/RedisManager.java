package com.example.demo.redis.redismanager;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisManager
 *
 * @author maco
 * @data 2019/10/24
 */
public class RedisManager {
    //@Value("${spring.redis.host}")
    private static String redisHost = "49.234.28.27";

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig, redisHost);
    }

    /**
     * 单例模式返回JedisPool对象
     *
     * @return
     * @throws Exception
     */
    public static Jedis getJedis() throws Exception {
        if (null != jedisPool) {
            return jedisPool.getResource();
        }
        throw new Exception("Jedispool was not init !!!");
    }
}
