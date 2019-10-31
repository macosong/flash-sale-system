package com.example.demo.redis;

/**
 * KeyPrefix
 *
 * @author maco
 * @data 2019/10/25
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
