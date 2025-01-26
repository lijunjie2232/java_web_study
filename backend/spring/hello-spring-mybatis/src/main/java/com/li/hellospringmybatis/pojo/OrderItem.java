package com.li.hellospringmybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Integer id;
    private Integer orderId;
//    private Integer goodsId;
    private Goods goods;
    private Integer quantity;
    private Double price;
}