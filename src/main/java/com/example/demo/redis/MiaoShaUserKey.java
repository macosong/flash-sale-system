package com.example.demo.redis;

/**
 * MiaoShaUserKey
 *
 * @author maco
 * @data 2019/10/25
 */
public class MiaoShaUserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    public static MiaoShaUserKey token = new MiaoShaUserKey(TOKEN_EXPIRE, "tk");
    public static MiaoShaUserKey getByNichName = new MiaoShaUserKey(0,"nickName");
    public MiaoShaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}