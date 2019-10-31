package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    public void redisTester(){
        Jedis jedis = new Jedis("49.234.28.27", 6379, 10000);
        System.out.println("服务正在运行: "+jedis.ping());
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        if(stringRedisTemplate.hasKey("hello")){
            String hello = stringRedisTemplate.opsForValue().get("hello");
            System.out.println("从redis中获取 key-hello--value : "+hello);
            stringRedisTemplate.opsForValue().set("jane","is a boy");
        }
    }
}
