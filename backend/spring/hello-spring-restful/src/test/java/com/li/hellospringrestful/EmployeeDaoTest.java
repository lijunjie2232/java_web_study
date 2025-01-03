package com.li.hellospringrestful;

import com.li.hellospringrestful.dao.EmployeeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeDaoTest {

    @Autowired
    EmployeeDao employeeDaoImpl;
    @Test
    public void getEmployeeByIdTest(){
        System.out.println(employeeDaoImpl.getEmployeeById(1));
        System.out.println(employeeDaoImpl.getEmployeeById(2));
    }
}
