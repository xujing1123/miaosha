package com.xujing.miaosha.redis;

public class MiaoshaKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public MiaoshaKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");

}
