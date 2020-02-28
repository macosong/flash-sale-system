package com.example.demo.Access;

import com.example.demo.redis.BasePrefix;

/**
 * AccessKey
 *
 * @author: songqiang
 * @date: 2020/1/17
 */
public class AccessKey extends BasePrefix {
    private AccessKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
