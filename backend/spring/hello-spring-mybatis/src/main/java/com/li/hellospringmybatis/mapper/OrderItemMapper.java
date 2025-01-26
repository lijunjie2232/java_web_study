package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> findAllOrderItems();
    OrderItem findOrderItemById(Integer id);
    List<OrderItem> findOrderItemsByOrderId(Integer orderId);
    void insertOrderItem(OrderItem orderItem);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(Integer id);
}
