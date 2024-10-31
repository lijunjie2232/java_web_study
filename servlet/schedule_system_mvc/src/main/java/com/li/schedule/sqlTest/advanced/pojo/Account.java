package com.li.schedule.sqlTest.advanced.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable {
    Integer id;
    Double balance = 0.0;
}
