package com.li.hellospring2.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class Person {
    private String name;
    private int age;
    public Person(){
        System.out.println("person constructor");
    }
}
