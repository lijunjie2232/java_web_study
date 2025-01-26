package com.li.hellospringmvc1.bean;

import lombok.Data;

@Data
public class Handle06Form {
    //    username=123&password=321&sex=male&grade=1&address.province=Kyoto&address.city=a&favorite=football&favorite=swimming
    private String username;
    private String password;
    private String sex;
    private int grade;
    private Address address;
    private String street;
    private String zipCode;
    private String[] favorite;
}

@Data
class Address {
    private String province;
    private String city;
}