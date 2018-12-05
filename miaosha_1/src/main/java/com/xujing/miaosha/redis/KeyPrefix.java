package com.xujing.miaosha.redis;

public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
