package com.li.hellospringmybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
    /*
    create table `emp`
    (
        `id`     int(11)        not null auto_increment,
        `name`   varchar(255)   not null,
        `age`    int(11)        not null,
        `salary` decimal(10, 2) not null,
        primary key (`id`)
    );
     */

    private Integer id;
    private String name;
    private Integer age;
    private BigDecimal salary;

}
