package com.li.hellospring2.dao;

import com.li.hellospring2.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Account getAccountById(Integer id) {
        String sql = "SELECT * FROM `account` WHERE `id`=?";
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), id);
        return account;
    }

    public void addAccount(Account account) {
        String sql = "INSERT INTO `account`(`username`, `age`, `balance`) VALUES (?,?,?)";
        jdbcTemplate.update(sql, account.getUsername(), account.getAge(), account.getBalance());
    }
    public void deleteAccount(Account account){
        String sql = "DELETE FROM `account` WHERE `id`=?";
        jdbcTemplate.update(sql, account.getId());
    }

    public void updateBalance(Integer id, BigDecimal balance){
        String sql = "update `account` set `balance` = `balance` + ? where `id` = ?";
        jdbcTemplate.update(sql, balance, id);
    }
}
