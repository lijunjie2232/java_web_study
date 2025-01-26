package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Order;
import com.li.hellospringmybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> findAllOrders();
    Order findOrderById(Integer id);
    void insertOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Integer id);
    List<Order> findOrderByUser(User user);
}
