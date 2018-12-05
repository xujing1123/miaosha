package com.xujing.miaosha.redis;

public class GoodsKey extends BasePrefix{

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public GoodsKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    @Override
    public int expireSeconds() {
        return super.expireSeconds();
    }

    @Override
    public String getPrefix() {
        return super.getPrefix();
    }
}
