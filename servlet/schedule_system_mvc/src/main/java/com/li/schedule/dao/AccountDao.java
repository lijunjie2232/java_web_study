package com.li.schedule.dao;

import com.li.schedule.pojo.SysUser;
import com.li.schedule.sqlTest.advanced.pojo.Account;

import java.util.List;

public interface AccountDao {
    /**
     * get all users
     * @return all users in List
     */
    List<Account> selectAll();

    /**
     * get one user by uid
     * @param id: uid of user
     * @return target user
     */
    Account selectById(Integer id);

    /**
     * add account
     * @return Account object
     */
    Account addAccount();

    /**
     * delect account
     * @param id account id
     * @return affect row
     */
    int delete(Integer id) throws Exception;

    /**
     * change balance
     * @param account Account object
     * @param balance changed balance mount
     * @return affect row
     */
    int balanceChange(Account account, Double balance) throws Exception;

}
