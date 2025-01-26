package com.li.hellospringbootbase1;

import com.li.hellospringbootbase1.properties.MyAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AutoConfTest {
    @Autowired
    private MyAutoConfiguration myAutoConfiguration;

    @Test
    void test(){
        System.out.println(myAutoConfiguration);
    }

}
