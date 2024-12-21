package com.li.hellospring2;

import com.li.hellospring2.bean.Account;
import com.li.hellospring2.bean.Book;
import com.li.hellospring2.dao.AccountDao;
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

    @Autowired
    AccountDao accountDao;

    @Test
    void datasourceTest() throws SQLException {
        Connection conn = dataSource.getConnection();
        System.out.println(conn);
        System.out.println(this.jdbcTemplate.queryForList("select * from book"));
    }

    @Test
    void getBookByIdTest() {
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

//    @Test
//    void updateBookStockTest(){
//        bookDao.updateBookStock(5, 0);
//        System.out.println(bookDao.getBookById(5));
//    }

//    @Test
//    void deleteBookTest() {
//        Book book = new Book();
//        book.setId(14);
//        bookDao.deleteBook(book);
//    }

    @Test
    void getAccountById(){
        Account account = accountDao.getAccountById(4);
        System.out.println(account);
    }
//    @Test
//    void addAccountTest() {
//        Account account = new Account();
//        account.setUsername("abc");
//        account.setAge(64);
//        account.setBalance(new BigDecimal(5475345.00));
//        accountDao.addAccount(account);
//    }


//    @Test
//    void deleteAccountTest() {
//        Account account = new Account();
//        account.setId(14);
//        accountDao.deleteAccount(account);
//    }

    @Test
    void updateBalanceTest() {
        Account account = accountDao.getAccountById(4);
        System.out.println(account);
        accountDao.updateBalance(4, new BigDecimal(1));
        account = accountDao.getAccountById(4);
        System.out.println(account);
        accountDao.updateBalance(4, new BigDecimal(-1));
        account = accountDao.getAccountById(4);
        System.out.println(account);
    }

}
