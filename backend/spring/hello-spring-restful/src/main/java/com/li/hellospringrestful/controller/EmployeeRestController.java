package com.li.hellospringrestful.controller;

import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }


}
