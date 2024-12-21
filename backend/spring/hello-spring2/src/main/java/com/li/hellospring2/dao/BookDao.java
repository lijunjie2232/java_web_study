package com.li.hellospring2.dao;

import com.li.hellospring2.bean.Book;
import com.li.hellospring2.util.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDao {
    JdbcTemplate jdbcTemplate;
    public Book getBookById(Integer id){
        String sql = "SELECT * FROM `book` WHERE `id`=?";
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
        return book;
    }
}
