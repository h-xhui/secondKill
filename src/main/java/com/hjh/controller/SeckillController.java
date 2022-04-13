package com.hjh.controller;

import com.hjh.pojo.Order;
import com.hjh.pojo.SeckillMessage;
import com.hjh.pojo.SeckillOrder;
import com.hjh.pojo.User;
import com.hjh.rabbitmq.MQSender;
import com.hjh.service.GoodsService;
import com.hjh.service.OrderService;
import com.hjh.service.SeckillOrderService;
import com.hjh.utils.JsonUtil;
import com.hjh.vo.GoodsVo;
import com.hjh.vo.RespBean;
import com.hjh.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;

    private Object lock = new Object();
    private Map<Long, Boolean> emptyStockMap = new ConcurrentHashMap<>(128);

    @RequestMapping(value = "/{path}/seckill", method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(@PathVariable String path, User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        boolean check = orderService.checkPath(user, goodsId, path);
        if (!check) {
            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SeckillOrder seckillOrder = (SeckillOrder) valueOperations.get("order:" + user.getId() + ":" + goodsId);
        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEAT_BUY);
        }
        // 内存标记
        if (emptyStockMap.get(goodsId)) {
            return RespBean.error(RespBeanEnum.STOCK_EMPTY);
        }
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if (stock == null || stock < 0) {
            emptyStockMap.put(goodsId, true);
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.STOCK_EMPTY);
        }
        SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
        mqSender.sendSeckillMessage(JsonUtil.obj2String(seckillMessage));
        return RespBean.success(0);
        //Order order = orderService.seckill(user, goods);
//        synchronized (lock) {
//            GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
//            if (goodsVo.getStockCount() < 1) {
//                model.addAttribute("errMsg", RespBeanEnum.STOCK_EMPTY.getMessage());
//                return "error/404";
//            }
//
//            //SeckillOrder seckillOrder = seckillOrderService.getOrderByUserAndGoods(user.getId(), goodsId);
//            SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//            if (seckillOrder != null) {
//                model.addAttribute("errMsg", RespBeanEnum.REPEAT_BUY.getMessage());
//                return "error/404";
//            }
//
//            Order order = orderService.seckill(user, goodsVo);
//            model.addAttribute("goods", goodsVo);
//            model.addAttribute("orderInfo", order);
//        }
    }

    @GetMapping("/result")
    @ResponseBody
    public RespBean getResult(User user, Long goodsId)  {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        Long orderId = seckillOrderService.getResult(user, goodsId);
        return RespBean.success(orderId);
    }

    @GetMapping("/path")
    @ResponseBody
    public RespBean getPath(User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        String res = orderService.createPath(user, goodsId);
        return RespBean.success(res);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.getAllGoodsVo();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        list.forEach(goodsVo -> {
            emptyStockMap.put(goodsVo.getId(), false);
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
        });
    }
}
