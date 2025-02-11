package com.li.hellospringbootredis;

import com.li.hellospringbootredis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test() {
        redisService.setString("redistest_user", "li");
        System.out.println(redisService.getString("redistest_user"));
    }

}
