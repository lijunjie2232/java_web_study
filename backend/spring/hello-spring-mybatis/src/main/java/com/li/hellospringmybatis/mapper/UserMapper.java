package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Order;
import com.li.hellospringmybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllUsers();
    User findUserById(Integer id);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(Integer id);
}
