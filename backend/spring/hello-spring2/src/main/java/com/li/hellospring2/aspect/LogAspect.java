package com.li.hellospring2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.TypeUtils;

import java.util.Arrays;

@Order
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(int *(int , int))")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @AfterReturning(value = "execution(int *(int , int))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Method class: " + methodSignature.getDeclaringTypeName());
        System.out.println("Method class: " + methodSignature.getDeclaringType());
        System.out.println("Method name: " + methodSignature.getName());
        System.out.println("method args: " + joinPoint.getArgs());
        System.out.println("result: " + result);
        System.out.println("afterReturning");
    }

    @AfterThrowing(value = "execution(int *(int , int))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Method name: " + methodSignature.getName());
        System.out.println("method args: " + joinPoint.getArgs());
        System.out.println("get exception: " + e.getMessage());
        System.out.println("afterThrowing");
    }

    @After("execution(int *(int , int))")
    public void after(JoinPoint joinPoint) {
        System.out.println("method class: " + joinPoint.getTarget().getClass().getName());
        System.out.println("method name: " + joinPoint.getSignature().getName());
        System.out.println("method args: " + joinPoint.getArgs());
        System.out.println("after");
    }

    @Around("pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        System.out.println("around args: " + Arrays.toString(args));
        Object result = null;
        try {
            System.out.println("around before");
            result = pjp.proceed(args);
            System.out.println("around after returning");
        } catch (Exception e) {
            System.out.println("around after throwing");
            throw e;
        } finally {
            System.out.println("around after");
        }
        return result;
    }

}
