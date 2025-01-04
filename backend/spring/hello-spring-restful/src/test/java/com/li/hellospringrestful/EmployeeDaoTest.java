package com.li.hellospringrestful;

import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.dao.EmployeeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class EmployeeDaoTest {

    @Autowired
    EmployeeDao employeeDaoImpl;

//    @Test
//    public void getEmployeeByIdTest() {
//        System.out.println(employeeDaoImpl.getEmployeeById(1));
//        System.out.println(employeeDaoImpl.getEmployeeById(2));
//    }
//
//    @Test
//    public void addEmployeeTest() {
//        employeeDaoImpl.addEmployee(
//                new Employee(
//                        null,
//                        "test",
//                        18,
//                        "test@qq.com",
//                        1,
//                        "test",
//                        new BigDecimal(1000)
//                )
//        );
//    }
//
//    @Test
//    public void deleteEmployeeTest() {
//        employeeDaoImpl.deleteEmployee(6);
//    }
//
//    @Test
//    public void updateEmployeeTest() {
//        System.out.println(employeeDaoImpl.getEmployeeById(7));
//        employeeDaoImpl.updateEmployee(
//                new Employee(
//                        7,
//                        "test",
//                        18,
//                        "test@qq.com",
//                        1,
//                        "test",
//                        new BigDecimal(5000)
//                )
//        );
//        System.out.println(employeeDaoImpl.getEmployeeById(7));
//    }
}
