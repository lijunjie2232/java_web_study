package com.li.hellospring2.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Before("execution(int *(int , int))")
    public void before() {
        System.out.println("before");
    }

    @After("execution(int *(int , int))")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("execution(int *(int , int))")
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("execution(int *(int , int))")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }
}
