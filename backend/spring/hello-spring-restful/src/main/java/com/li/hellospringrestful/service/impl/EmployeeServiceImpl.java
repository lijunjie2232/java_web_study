package com.li.hellospringrestful.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.dao.EmployeeDao;
import com.li.hellospringrestful.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;


    @Override
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeDao.deleteEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        assert employee.getId() != null;
        Employee employeeOrigin = employeeDao.getEmployeeById(employee.getId());
        employeeOrigin.setName(StringUtils.hasText(employee.getName()) ? employee.getName() : employeeOrigin.getName());
        employeeOrigin.setAge(employee.getAge() != null ? employee.getAge() : employeeOrigin.getAge());
        employeeOrigin.setEmail(StringUtils.hasText(employee.getEmail()) ? employee.getEmail() : employeeOrigin.getEmail());
        employeeOrigin.setGender(employee.getGender() != null ? employee.getGender() : employeeOrigin.getGender());
        employeeOrigin.setAddress(StringUtils.hasText(employee.getAddress()) ? employee.getAddress() : employeeOrigin.getAddress());
        employeeOrigin.setSalary(employee.getSalary() != null ? employee.getSalary() : employeeOrigin.getSalary());
        employeeDao.updateEmployee(employeeOrigin);
    }

    @Override
    public Employee getEmployee(int id) {
        return employeeDao.getEmployeeById(id);
    }
}
