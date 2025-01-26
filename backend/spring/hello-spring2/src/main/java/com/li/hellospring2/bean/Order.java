package com.li.hellospring2.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Map<Integer, Integer> orderMap = new HashMap<>();
    private Integer acccountId = null;

    public void addItem(Integer bid, Integer num) {
        if (orderMap.containsKey(bid)) {
            orderMap.put(bid, orderMap.get(bid) + num);
        } else {
            orderMap.put(bid, num);
        }
    }
}
