package com.my.kotlin.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate redisTemplate;   //key-value是对象的

    //判断是否存在key
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    //从redis中获取值
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //向redis插入值
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //向redis插入值带过期时间 单位:分钟
    public boolean set(final String key, Object value, long time) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //redis删除值
    public boolean del(final String key) {
        return redisTemplate.delete(key);
    }

    //批量删除某个字段开始的key
    public long batchDel(String key) {
        Set<String> set = redisTemplate.keys(key + "*");
        return redisTemplate.delete(set);
    }

}
