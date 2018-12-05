package com.xujing.miaosha.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("117.48.215.192",6385);
        jedis.auth("123456");
        System.out.println("Connet to Redis-Server Successfully");

        //check whether server is running or not
        // 如果返回PONG,说明正在运行
        System.out.println(jedis.ping());
        System.out.println("---------------------List--------------------------------");
        //存储List缓存数据
        jedis.lpush("test-list", "Java");
        jedis.lpush("test-list", "PHP");
        jedis.lpush("test-list", "C++");
        //获取list缓存数据
        List<String> listCache = jedis.lrange("test-list", 0, 3);
        for (int i = 0; i < listCache.size(); i++) {
            System.out.println("缓存输出：" + listCache.get(i));
        }
        System.out.println("---------------------HashMap--------------------------------");
        //存储Hash类型缓存数据
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("xiaoming", "man");
        hashMap.put("xiaohua", "women");
        hashMap.put("xiaoma", "man");
        jedis.hmset("test-hash", hashMap);
        //获取hash类型缓存数据
        Map<String, String> hashData = jedis.hgetAll("test-hash");
        System.out.println("获取hash缓存数据（xiaoming）："+hashData.get("xiaoming"));
        System.out.println("获取hash缓存数据（xiaohua）："+hashData.get("xiaohua"));
        System.out.println("获取hash缓存数据（xiaoma）："+hashData.get("xiaoma"));

        System.out.println("---------------------Set--------------------------------");
        //存储Set缓存数据
        jedis.sadd("test-set", "Java");
        jedis.sadd("test-set", "PHP");
        jedis.sadd("test-set", "C++");
        jedis.sadd("test-set", "PHP");
        //获取set缓存数据
        Set<String> setCache = jedis.smembers("test-set");
        for(String setStr : setCache){
            System.out.println("Set集合缓存输出:" + setStr);
        }

        System.out.println("---------------------ZSet--------------------------------");
        //存储zset类型缓存数据
        jedis.zadd("test-zset",1,"math");
        jedis.zadd("test-zset",3,"enlish");
        jedis.zadd("test-zset",2,"PHP");
        jedis.zadd("test-zset",2,"PHP");
        //获取zset缓存数据类型
        Set<String> setCache2 = jedis.zrange("test-zset", 0, 5);
        for(String setStr : setCache2){
            System.out.println("获取zset缓存数据：" + setStr);
        }


    }
}
