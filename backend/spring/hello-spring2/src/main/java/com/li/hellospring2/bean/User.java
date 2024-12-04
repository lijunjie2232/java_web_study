package com.li.hellospring2.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@ToString
@Data
@AllArgsConstructor
public class User implements InitializingBean , DisposableBean {
    private String name;

    public User() {
        System.out.println("user constructor");
    }

    public void init(){
        System.out.println("user init");
    }
    public void destroy(){
        System.out.println("user destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
