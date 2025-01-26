package com.li.hello_spring_practice1;

import com.li.hello_spring_practice1.bean.Log;
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
    @Test
    public void addLogTest() {
        logDao.addLog(new Log("li"));
    }

    @Test
    public void deleteLogTest() {
        logDao.deleteLog(7);
    }

    @Test
    public void getAllLogsTest() {
        System.out.println(logDao.getAllLogs(0, 5));
    }
}
