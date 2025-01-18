package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> findAllGoods();
    Goods findGoodsById(Integer id);
    void insertGoods(Goods goods);
    void updateGoods(Goods goods);
    void deleteGoods(Integer id);
}
