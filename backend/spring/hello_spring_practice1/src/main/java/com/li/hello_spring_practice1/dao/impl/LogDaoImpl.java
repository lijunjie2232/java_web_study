package com.li.hello_spring_practice1.dao.impl;

import com.li.hello_spring_practice1.bean.Log;
import com.li.hello_spring_practice1.dao.LogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogDaoImpl implements LogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Log getLogById(int id) {
        String sql = "select * from log where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Log>(Log.class), id);
    }

    @Override
    public void addLog(Log log) {
        String sql = "insert into log (username) values (?)";
        jdbcTemplate.update(sql, log.getUsername());
    }

    @Override
    public void deleteLog(int id) {
        String sql = "delete from log where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Log> getAllLogs(int offset, int pageSize) {
        String sql = "select * from log limit ?, ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Log>(Log.class), offset, offset + pageSize);
    }
}
