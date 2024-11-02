package com.li.schedule.dao;

import com.li.schedule.pojo.SysUser;
import java.util.List;

/**
 * sys_user operations
 * author: @lijunjie2232
 */
public interface SysUserDao {

    /**
     * get all users
     * @return all users in List
     */
    List<SysUser> selectAll();

    /**
     * get one user by uid
     * @param uid: uid of user
     * @return target user
     */
    SysUser selectByUid(Integer uid);
    /**
     * get one user by username
     * @param username: username of user
     * @return target user
     */
    SysUser selectByUsername(String username);

    /**
     * add user to table sys_user
     * @param user SysUser object
     * @return affect row == 0 means not succeed yet
     */
    SysUser addUser(SysUser user);

    /**
     * update user
     * @param user
     * @return affect row
     */
    int updateUser(SysUser user);

    /**
     * delete user by uid
     * @param uid
     * @return affect row
     */
    int deleteByUid(Integer uid);
}
