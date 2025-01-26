package com.li.hellospring2.proxy.dynamic;

import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static Object newProxyInstance(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("[" + method + "] : args: " + args);
                    return method.invoke(target, args);
                }
        );
    }
}
