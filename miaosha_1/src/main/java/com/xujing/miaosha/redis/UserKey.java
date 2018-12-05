package com.xujing.miaosha.redis;

public class UserKey extends BasePrefix{


    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByName = new UserKey("name");
}
