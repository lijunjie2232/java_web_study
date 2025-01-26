package com.li.hello_spring_practice1.dao;

import com.li.hello_spring_practice1.bean.Employee;
import com.li.hello_spring_practice1.bean.Log;

import java.util.List;

public interface LogDao {
    public Log getLogById(int id);
    public void addLog(Log log);
    public void deleteLog(int id);
    public List<Log> getAllLogs(int offset, int pageSize);
}
