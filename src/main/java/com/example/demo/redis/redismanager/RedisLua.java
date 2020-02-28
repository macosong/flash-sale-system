package com.example.demo.redis.redismanager;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * RedisLua
 *
 * @author maco
 * @data 2019/10/24
 */
@Slf4j
public class RedisLua {

    /**
     * 访问redis的key，并执行luaStr
     *
     * @param key
     * @param luaStr
     * @return
     */
    public static Object redisVisit(String key, String luaStr) {
        Jedis jedis;
        Object object = null;
        try {
            jedis = RedisManager.getJedis();
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> argves = new ArrayList<>();
            String luaScript = jedis.scriptLoad(luaStr);
            object = jedis.evalsha(luaScript, keys, argves);
        } catch (Exception e) {
            log.error("统计访问次数失败！！！", e);
        }
        return object;
    }

    /**
     * 统计访问次数
     *
     * @param key
     * @return
     */
    public static Object getVistorCount(String key) {
        String getLua = "local num=redis.call('get',KEYS[1]) return num";
        return redisVisit(key, getLua);
    }

    /**
     * 访问次数增加
     *
     * @param key
     */
    public static void visitorCount(String key) {
        String incrLua = "local num=redis.call('incr',KEYS[1]) return num";
        redisVisit(key, incrLua);
    }

    public static void main(String[] args) {
        visitorCount("count:login");
    }
}
