package com.li.schedule.service.impl;

import com.li.schedule.dao.SysUserDao;
import com.li.schedule.dao.impl.SysUserDaoImpl;
import com.li.schedule.pojo.SysUser;
import com.li.schedule.service.SysUserService;
import com.li.schedule.util.MD5Util;

import java.util.List;

public class SysUserServiceImpl implements SysUserService {
    private SysUserDao sysUserDao = new SysUserDaoImpl();
    @Override
    public SysUser userLogin(String username, String password) {
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            password = MD5Util.MD5(password);
            SysUser user = this.sysUserDao.selectByUsername(username);
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public SysUser userSign(String username, String password) {
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            password = MD5Util.MD5(password);
            SysUser user = new SysUser(null, username, password, null);
            user = this.sysUserDao.addUser(user);
            return user;
        }
        return null;
    }

    @Override
    public List<SysUser> getAllUser(){
        return this.sysUserDao.selectAll();
    }
}
