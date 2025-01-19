package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Goods;
import com.li.hellospringmybatis.pojo.Order;
import com.li.hellospringmybatis.pojo.OrderItem;
import com.li.hellospringmybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderUserMapper {
    User getUserById(int id);

    List<Order> getOrderByUser(User user);

    List<OrderItem> getOrderItemByOrder(Order order);

    Goods getGoodsById(int id);
}
