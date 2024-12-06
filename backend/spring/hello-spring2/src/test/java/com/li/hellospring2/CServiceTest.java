package com.li.hellospring2;

import com.li.hellospring2.proxy.dynamic.DynamicProxy;
import com.li.hellospring2.service.CService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@SpringBootTest
public class CServiceTest {

    @Autowired
    CService cService;

    @Test
    void calcTest() {
        System.out.println(cService.add(1, 2));

        CService proxy = (CService) Proxy.newProxyInstance(
                cService.getClass().getClassLoader(),
                cService.getClass().getInterfaces(),
                // InvocationHandler
                (o, m, params) -> {
                    return m.invoke(cService, params);
                }
        );
        System.out.println(proxy.add(2, 3));

        CService proxy2 = (CService) DynamicProxy.newProxyInstance(cService);
        System.out.println(proxy2.mul(2, 3));
    }
}
