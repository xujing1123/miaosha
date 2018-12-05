package com.xujing.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    public MiaoshaUserKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"token");

    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
}
