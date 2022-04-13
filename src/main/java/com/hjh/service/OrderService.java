package com.hjh.service;

import com.hjh.pojo.Order;
import com.hjh.pojo.User;
import com.hjh.vo.GoodsVo;

/**
 * @author 洪锦辉
 * 2022/2/18
 */

public interface OrderService {
    Order seckill(User user, GoodsVo goodsVo);

    String createPath(User user, Long goodsId);

    boolean checkPath(User user, Long goodsId, String path);
}
