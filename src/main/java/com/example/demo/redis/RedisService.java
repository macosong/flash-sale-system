package com.example.demo.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * RedisService
 *
 * @author maco
 * @data 2019/10/25
 */
@Service
@Slf4j
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * 删除一条数据
     *
     * @param prefix
     * @param key
     * @return
     */
    public boolean delete(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            long ret = jedis.del(realKey);
            return ret > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 获取单个对象
     *
     * @param prefix    特定的key对象
     * @param key   根据业务类型自定义的key
     * @param clazz 目标value的数据类型
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的Key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (StringUtils.isEmpty(str)){
                return false;
            }
            //生成真正的key,并设置超时时间
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0){
                jedis.set(realKey, str);
            }else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }


    /**
     * 把类对象转化为字符串
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value){
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return "" + value;
        }else if (clazz == String.class){
            return (String)value;
        }else if (clazz == long.class || clazz == Long.class){
            return "" + value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 把str转化为对应的类对象
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }else if (clazz == String.class){
            return (T)str;
        }else if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 使用完jedis连接，释放
     *
     * @param jedis
     */
    private void returnToPool(Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }
}
