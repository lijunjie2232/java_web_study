package com.li.schedule.dao.impl;

import com.li.schedule.dao.SysUserDao;
import com.li.schedule.pojo.SysUser;

public class SysUserDaoImpl implements SysUserDao {

    private Integer sid;
    private Integer uid;
    private String title;
    private Integer completed;

    @Override
    public int addUser(SysUser user) {
        return 0;
    }
}
