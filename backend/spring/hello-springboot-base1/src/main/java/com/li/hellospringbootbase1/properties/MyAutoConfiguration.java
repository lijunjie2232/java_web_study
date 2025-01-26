package com.li.hellospringbootbase1.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "abc")
public class MyAutoConfiguration {
    private String a;
    private String b;
    private String c;
}
