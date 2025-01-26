package com.li.schedule.service.impl;

import org.junit.jupiter.api.Test;

public class SysUserServiceImplTest {

    @Test
    public void userLoginTest(){
        SysUserServiceImpl sysUserService = new SysUserServiceImpl();
        System.out.println(sysUserService.userLogin("admin","admin"));
    }
}
