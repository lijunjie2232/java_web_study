package com.li.hellospringrestful.service;

import com.li.hellospringrestful.bean.Employee;

public interface EmployeeService {

    /*
    for sql table:
    CREATE TABLE IF NOT EXISTS `employee`
    (
        `id`      INT(11)        NOT NULL AUTO_INCREMENT,
        `name`    VARCHAR(255)   NOT NULL,
        `age`     INT(11)        NOT NULL,
        `email`   VARCHAR(255)   NOT NULL,
        `gender`  INT1           NOT NULL,
        `address` VARCHAR(255)   NOT NULL,
        `salary`  DECIMAL(10, 2) NOT NULL,
        PRIMARY KEY (`id`) USING BTREE
    );
     */
    void addEmployee(Employee employee);

    void deleteEmployee(int id);

    void updateEmployee(Employee employee);

    Employee getEmployee(int id);
}
