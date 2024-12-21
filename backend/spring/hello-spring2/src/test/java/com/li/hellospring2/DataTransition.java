package com.li.hellospring2;

import com.li.hellospring2.bean.Book;
import com.li.hellospring2.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataTransition {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BookDao bookDao;

    @Test
    void datasourceTest() throws SQLException {
        Connection conn = dataSource.getConnection();
        System.out.println(conn);
        System.out.println(this.jdbcTemplate.queryForList("select * from book"));
    }

    @Test
    void getBookByIdTest(){
        Book book = bookDao.getBookById(5);
        System.out.println(book);
    }
//    @Test
//    void addBookTest(){
//        Book book = new Book();
//        book.setBookName("Java");
//        book.setPrice(new BigDecimal(100));
//        book.setStock(1);
//        bookDao.addBook(book);
//    }

}
