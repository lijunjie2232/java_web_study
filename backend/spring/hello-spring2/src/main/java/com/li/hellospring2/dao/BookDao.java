package com.li.hellospring2.dao;

import com.li.hellospring2.bean.Book;
import com.li.hellospring2.util.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Book getBookById(Integer id) {
        String sql = "SELECT * FROM `book` WHERE `id`=?";
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
        return book;
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO `book`(`bookName`, `price`, `stock`) VALUES (?,?,?)";
        jdbcTemplate.update(sql, book.getBookName(), book.getPrice(), book.getStock());
    }

    public void updateBookStock(Integer bookId, Integer num){
        String sql = "UPDATE `book` SET stock=? WHERE `id`=?";
        jdbcTemplate.update(sql, num, bookId);
    }

    public void deleteBook(Book book){
        String sql = "DELETE FROM `book` WHERE `id`=?";
        jdbcTemplate.update(sql, book.getId());
    }

}
