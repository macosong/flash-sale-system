package com.example.demo.redis;

/**
 * BasePrefix
 *
 * @author maco
 * @data 2019/10/25
 */
public class BasePrefix  implements KeyPrefix{
    private  int expireSeconds;

    private String prefix;

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 获取唯一的key
     *
     * @return
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}
