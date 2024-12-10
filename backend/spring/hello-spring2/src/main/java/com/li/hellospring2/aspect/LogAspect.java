package com.li.hellospring2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Before("execution(int *(int , int))")
    public void before() {
        System.out.println("before");
    }

    @AfterReturning(value = "execution(int *(int , int))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Method class:" + methodSignature.getDeclaringTypeName());
        System.out.println("Method class:" + methodSignature.getDeclaringType());
        System.out.println("Method name: " + methodSignature.getName());
        System.out.println("method args: " + joinPoint.getArgs());
        System.out.println("result: " + result);
        System.out.println("afterReturning");
    }

    @AfterThrowing("execution(int *(int , int))")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @After("execution(int *(int , int))")
    public void after(JoinPoint joinPoint) {
        System.out.println("method class: " + joinPoint.getTarget().getClass().getName());
        System.out.println("method name: " + joinPoint.getSignature().getName());
        System.out.println("method args: " + joinPoint.getArgs());
        System.out.println("after");
    }

}
