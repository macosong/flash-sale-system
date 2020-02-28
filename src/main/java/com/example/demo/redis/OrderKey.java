package com.example.demo.redis;

/**
 * OrderKey
 *
 * @author: songqiang
 * @date: 2019/12/16
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaosaOrderByUidGid = new OrderKey("moug");
}
