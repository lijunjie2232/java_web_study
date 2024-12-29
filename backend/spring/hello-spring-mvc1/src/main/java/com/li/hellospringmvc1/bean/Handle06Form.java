package com.li.hellospringmvc1.bean;

import lombok.Data;

@Data
public class Handle06Form {
//    username=123&password=321&sex=male&grade=2&favorite=football&favorite=swimming
    private String username;
    private String password;
    private String sex;
    private int grade;
    private String[] favorite;
}
