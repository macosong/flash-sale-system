package com.example.demo.redis.redismanager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis中保存的数据：
 * 1、网站访问次数
 * 2、用户nickname
 * 3、用户UUID ： MiaoshaUser string
 * 4、秒杀商品页面html
 */

/**
 * RedisManager
 *
 * @author maco
 * @data 2019/10/24
 */
public class RedisManager {
    private static String redisHost = "49.234.28.27";

    private static JedisPool jedisPool;

    static{
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
    public static Jedis getJedis() throws Exception{
        if (null != jedisPool){
            return jedisPool.getResource();
        }
        throw new Exception("Jedispool was not init !!!");
    }
}
