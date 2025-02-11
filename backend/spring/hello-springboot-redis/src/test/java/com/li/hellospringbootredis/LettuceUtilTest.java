package com.li.hellospringbootredis;

import com.li.hellospringbootredis.utils.JedisUtil;
import com.li.hellospringbootredis.utils.LettuceUtil;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Set;

@SpringBootTest
public class LettuceUtilTest {
    @Autowired
    LettuceUtil lu;

    @Test
    public void testJedisUtil() {
        try (StatefulRedisConnection connection = lu.getConnection()) {
            RedisCommands commands = connection.sync();
            System.out.println(commands.get("user"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
