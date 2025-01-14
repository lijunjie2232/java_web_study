package com.li.hello_spring_practice1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.li.hello_spring_practice1.dao.LogDao;

@SpringBootTest
public class LogDaoTest {

    @Autowired
    private LogDao logDao;
    @Test
    public void getLogByIdTest() {
        System.out.println(logDao.getLogById(1));
    }
}
