package com.hjh.service;

import com.hjh.pojo.SeckillOrder;
import com.hjh.pojo.User;

/**
 * @author 洪锦辉
 * 2022/2/18
 */

public interface SeckillOrderService {
    SeckillOrder getOrderByUserAndGoods(Long userId, Long goodsId);

    Long getResult(User user, Long goodsId);
}
