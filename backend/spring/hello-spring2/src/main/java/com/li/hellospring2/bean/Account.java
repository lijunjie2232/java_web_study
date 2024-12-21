package com.li.hellospring2.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private String username;
    private Integer age;
    private BigDecimal balance;
}
