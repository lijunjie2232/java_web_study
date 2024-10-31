package com.li.schedule.dao.impl;

import com.li.schedule.dao.AccountDao;
import com.li.schedule.sqlTest.advanced.pojo.Account;

import java.util.List;

public class AccountDaoImpl extends BaseDao implements AccountDao {
    @Override
    public List<Account> selectAll() {
        try {
            String sql = "select * from `account`";
            return this.executeQuery(Account.class, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account selectById(Integer id) {
        String sql = "SELECT * FROM `account` WHERE `id`=?";
        Account account = null;
        try {
            account = this.executeQuery4OneRow(Account.class, sql, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account addAccount() {
        Account account = new Account();
        String sql = "INSERT INTO `account`() VALUES ()";
        try {
            account.setId(this.executeInsertWithGenKey(sql));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public int delete(Integer id) throws Exception {
        String sql = "DELETE FROm `account` WHERE `id`=?";

        return this.executeUpdate(sql, id);

    }


    @Override
    public int balanceChange(Account account, Double balance) throws Exception {
        String sql = "UPDATE `account` SET `balance`=`balance`+? WHERE `id`=?";

        return this.executeUpdate(sql, balance, account.getId());

    }
}
