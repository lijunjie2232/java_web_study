package com.li.schedule.dao;

import com.li.schedule.pojo.SysUser;

/**
 * sys_user operations
 * author: @lijunjie2232
 */
public interface SysUserDao {

    /**
     * add user to table sys_user
     * @param user SysUser object
     * @return affect row == 0 means not succeed yet
     */
    int addUser(SysUser user);
}
