package com.li.hellospring2.proxy.statics;

import com.li.hellospring2.service.CService;
import lombok.Data;
import org.springframework.stereotype.Component;

//@Component
@Data
public class CServiceStaticProxy implements CService {
    private CService cService;

    public CServiceStaticProxy(CService cService) {
        this.cService = cService;
    }

    @Override
    public int add(int a, int b) {
        return cService.add(a, b);
    }

    @Override
    public int sub(int a, int b) {
        return cService.sub(a, b);
    }

    @Override
    public int mul(int a, int b) {
        return cService.mul(a, b);
    }

    @Override
    public int div(int a, int b) {
        return cService.div(a, b);
    }
}
