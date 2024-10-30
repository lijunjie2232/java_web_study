package com.li.schedule.dao.impl;

import com.li.schedule.dao.SysUserDao;
import com.li.schedule.pojo.SysUser;

import java.util.LinkedList;
import java.util.List;

public class SysUserDaoImpl extends BaseDao implements SysUserDao {

    @Override
    public List<SysUser> selectAll() {
        String sql = "SELECT * FROM `sys_user`";
        List<SysUser> users = null;
        try {
            users = this.executeQuery(SysUser.class, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public SysUser selectByUid(Integer uid) {
        String sql = "SELECT * FROM `sys_user` WHERE `uid`=?";
        SysUser user = null;
        try {
            user = this.executeQuery4OneRow(SysUser.class, sql, uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public SysUser addUser(SysUser user) {
        String sql = "INSERT INTO `sys_user`(`username`, `password`) VALUES (?,?)";
        try {
            user.setUid(this.executeInsertWithGenKey(sql, user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int updateUser(SysUser user) {
        String sql = "UPDATE `sys_user` SET `username`=?";
        if (user.getPassword() != null && !user.getPassword().equals(""))
            sql += ", `password`=?";
        sql += " WHERE `uid`=?";
        try {
            if (user.getPassword() != null && !user.getPassword().equals(""))
                return this.executeUpdate(sql, user.getUsername(), user.getPassword(), user.getUid());
            else
                return this.executeUpdate(sql, user.getUsername(), user.getUid());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByUid(Integer uid) {
        return 0;
    }
}
