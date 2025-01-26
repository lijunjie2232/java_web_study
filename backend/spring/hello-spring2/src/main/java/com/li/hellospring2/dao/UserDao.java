package com.li.hellospring2.dao;

import com.li.hellospring2.util.DataSource;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@ToString
@Repository
public class UserDao {

    @Autowired
    DataSource dataSource;
}
