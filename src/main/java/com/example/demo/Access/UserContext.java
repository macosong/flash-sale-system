package com.example.demo.Access;

import com.example.demo.domain.MiaoshaUser;

/**
 * UserContext
 *
 * @author: songqiang
 * @date: 2020/1/17
 */
public class UserContext {
    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();
    }
}
