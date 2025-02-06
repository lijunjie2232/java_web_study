package com.li.hellospringbootredis;

import com.li.hellospringbootredis.utils.JedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Set;

@SpringBootTest
public class JedisUtilTest {
    @Autowired
    JedisUtil ju;

    @Test
    public void configTest(
            @Value("${redis.host:127.0.0.1}") String host,
            @Value("${redis.port:6379}") String port,
            @Value("${redis.password:redis}") String password
    ) {
        System.out.println(host);
        System.out.println(port);
        System.out.println(password);
        JedisUtil jedisUtil = new JedisUtil(host, port, password);
        System.out.println(jedisUtil.ping());
    }

    @Test
    public void testJedisUtil() {
        System.out.println(ju);
        System.out.println(ju.ping());
        ju.set("jtestkey", "jtestvalue");
        System.out.println(ju.get("jtestkey"));
        Jedis j = ju.getJedis();
        j.set("jtestkey2", "jtestvalue2");
        System.out.println(j.get("jtestkey2"));

        Set<String> keys = j.keys("*");
        System.out.println(keys);

        j.lpush("jmsg", "msg1", "msg2", "msg3");
        System.out.println(j.lrange("jmsg", 0, -1));

        System.out.println(j.ttl("user"));

    }
}
