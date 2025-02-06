package com.li.hellospringbootredis.utils;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;


@NoArgsConstructor
@Component
public class LettuceUtil {

    @Value("${redis.host:127.0.0.1}")
    String host;
    @Value("${redis.port:6379}")
    Integer port;
    @Value("${redis.password:redis}")
    String password;
    private RedisClient client;

    public void _init(
            @Value("${redis.host:127.0.0.1}") String host,
            @Value("${redis.port:6379}") Integer port,
            @Value("${redis.password:redis}") String password
    ) {
        System.out.println("*******************LettuceUtil init*******************");
        RedisURI uri = RedisURI.Builder
                .redis(host)
                .withPort(port)
                .withAuthentication("default", password)
                .withTimeout(Duration.ofSeconds(10))
                .build();
        this.client = RedisClient.create(uri);
        System.out.println("*******************LettuceUtil init*******************");
    }

    @PostConstruct
    public void init(
    ) {
        this._init(host, port, password);
    }

    public LettuceUtil(
            @Value("${redis.host:127.0.0.1}") String host,
            @Value("${redis.port:6379}") Integer port,
            @Value("${redis.password:redis}") String password
    ) {
        this._init(host, port, password);
    }

    public StatefulRedisConnection<String, String> getConnection() {
        return client.connect();
    }


    @PreDestroy
    public void destroy() {
        System.out.println("*******************LettuceUtil destroy*******************");
        client.shutdown();
        System.out.println("*******************LettuceUtil destroy*******************");
    }
}
