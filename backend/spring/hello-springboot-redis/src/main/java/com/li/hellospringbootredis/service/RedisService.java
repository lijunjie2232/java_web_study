package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 存储字符串
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取字符串
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 存储哈希
    public void setHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // 获取哈希
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    // 存储列表
    public void setList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 获取列表
    public String getList(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }

    // 存储集合
    public void setSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    // 获取集合
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // 存储有序集合
    public void setZSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    // 获取有序集合
    public Set<String> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}
