package com.hjh.service.impl;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.hjh.mapper.OrderMapper;
import com.hjh.mapper.SeckillGoodsMapper;
import com.hjh.mapper.SeckillOrderMapper;
import com.hjh.pojo.Order;
import com.hjh.pojo.SeckillGoods;
import com.hjh.pojo.SeckillOrder;
import com.hjh.pojo.User;
import com.hjh.service.OrderService;
import com.hjh.utils.MD5Util;
import com.hjh.utils.UUIDUtil;
import com.hjh.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Order seckill(User user, GoodsVo goodsVo) {
        SeckillGoods seckillGoods = seckillGoodsMapper.getSeckillGoodsByGoodsId(goodsVo.getId());
        if (seckillGoods.getStockCount() < 1) {
            return null;
        }
        seckillGoodsMapper.reduceStockById(seckillGoods.getId(), seckillGoods.getStockCount()-1);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckilPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insertOrder(order);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goodsVo.getId(), seckillOrder);
        seckillOrderMapper.insertSeckillOrder(seckillOrder);

        return order;
    }

    @Override
    public String createPath(User user, Long goodsId) {
        String s = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:" + user.getId() + ":" + goodsId, s, 60, TimeUnit.SECONDS);
        return s;
    }

    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (user == null || goodsId < 0 || StringUtils.isEmpty(path)) {
            return false;
        }
        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);
        return path.equals(redisPath);
    }
}
