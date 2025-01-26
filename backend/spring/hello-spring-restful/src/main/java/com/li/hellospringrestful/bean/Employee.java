package com.li.hellospringrestful.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
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
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    private Integer gender;
    private String address;
    private BigDecimal salary;
}
