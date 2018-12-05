package com.xujing.miaosha.redis;

public class OrderKey extends BasePrefix{


    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
