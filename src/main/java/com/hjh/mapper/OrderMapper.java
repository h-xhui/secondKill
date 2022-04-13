package com.hjh.mapper;

import com.hjh.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Mapper
public interface OrderMapper {
    Order getOrderByUserAndGoods(Long userId, Long goodsId);
    Integer insertOrder(Order order);
}
