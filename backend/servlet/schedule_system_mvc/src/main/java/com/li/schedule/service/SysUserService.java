package com.li.schedule.service;

import com.li.schedule.pojo.SysUser;

import java.util.List;

public interface SysUserService {
    /**
     * user login
     * @param username: username
     * @param password: password
     * @return Sysuesr object if success else null
     */
    SysUser userLogin(String username, String password);
    SysUser userSign(String username, String password);
    List<SysUser> getAllUser();
}
