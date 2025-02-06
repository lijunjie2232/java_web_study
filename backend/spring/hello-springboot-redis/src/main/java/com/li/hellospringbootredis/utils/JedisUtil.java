package com.li.hellospringbootredis.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


@Component
public class JedisUtil {
    @Value("${redis.host:127.0.0.1}")
    String host;
    @Value("${redis.port:6379}")
    String port;
    @Value("${redis.password:redis}")
    private String password;

    private Jedis jedis;

    @PostConstruct
    public void init() {
        System.out.println("*******************JedisUtil init*******************");
        System.out.println("host: " + host);
        System.out.println("port: " + port);
        System.out.println("password: " + password);
        this.jedis = new Jedis(host, Integer.parseInt(port), 10000);
        this.jedis.auth(password);
    }

    public JedisUtil(
            @Value("${redis.host:127.0.0.1}") String host,
            @Value("${redis.port:6379}") String port,
            @Value("${redis.password:redis}") String password
    ) {
        this.jedis = new Jedis(host, Integer.parseInt(port), 10000);
        this.jedis.auth(password);
    }

    public String ping() {
        return this.jedis.ping();
    }

    public String set(String key, String value) {
        return this.jedis.set(key, value);
    }

    public String get(String key) {
        return this.jedis.get(key);
    }

    public long del(String key) {
        return this.jedis.del(key);
    }

    public Jedis getJedis() {
        return this.jedis;
    }

}
