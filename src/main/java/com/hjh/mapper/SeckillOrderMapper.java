package com.hjh.mapper;

import com.hjh.pojo.Order;
import com.hjh.pojo.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Mapper
public interface SeckillOrderMapper {
    Integer insertSeckillOrder(SeckillOrder seckillOrder);
    SeckillOrder getOrderByUserAndGoods(Long userId, Long goodsId);
}
