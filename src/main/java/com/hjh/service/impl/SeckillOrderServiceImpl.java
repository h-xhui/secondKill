package com.hjh.service.impl;

import com.hjh.mapper.SeckillOrderMapper;
import com.hjh.pojo.SeckillOrder;
import com.hjh.pojo.User;
import com.hjh.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public SeckillOrder getOrderByUserAndGoods(Long userId, Long goodsId) {
        return seckillOrderMapper.getOrderByUserAndGoods(userId, goodsId);
    }

    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder order = seckillOrderMapper.getOrderByUserAndGoods(user.getId(), goodsId);
        if (order != null) {
            return order.getOrderId();
        } else if ((Integer)redisTemplate.opsForValue().get("seckillGoods:" + goodsId) == 0) {
            return -1L;
        }
        return 0L;
    }
}
