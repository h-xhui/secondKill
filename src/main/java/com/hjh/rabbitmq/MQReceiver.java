package com.hjh.rabbitmq;

import com.hjh.pojo.SeckillMessage;
import com.hjh.pojo.SeckillOrder;
import com.hjh.pojo.User;
import com.hjh.service.GoodsService;
import com.hjh.service.OrderService;
import com.hjh.utils.JsonUtil;
import com.hjh.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消费者
 *
 * @author 洪锦辉
 * 2022/3/3
 */
@Service
public class MQReceiver {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderService orderService;
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message) {
        SeckillMessage seckillMessage = JsonUtil.string2Obj(message, SeckillMessage.class);
        Long goodsId = seckillMessage.getGoodsId();
        User user = seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() < 1) {
            return;
        }
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        // 防止重复抢购
        if (seckillOrder != null) {
            return;
        }
        // 下单
        orderService.seckill(user, goodsVo);
    }
}
