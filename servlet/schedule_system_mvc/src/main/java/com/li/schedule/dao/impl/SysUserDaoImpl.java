package com.li.schedule.dao.impl;

import com.li.schedule.dao.SysUserDao;
import com.li.schedule.pojo.SysUser;

import java.util.LinkedList;
import java.util.List;

public class SysUserDaoImpl extends BaseDao implements SysUserDao {

    private Integer sid;
    private Integer uid;
    private String title;
    private Integer completed;

    @Override
    public List<SysUser> selectAll() {
        List<SysUser> users = new LinkedList<>();
        return users;
    }

    @Override
    public SysUser selectByUid(Integer uid) {
        return null;
    }

    @Override
    public int addUser(SysUser user) {
        return 0;
    }

    @Override
    public int updateUser(SysUser user) {
        return 0;
    }

    @Override
    public int deleteByUid(Integer uid) {
        return 0;
    }
}
