package com.hjh.rabbitmq;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生产者
 *
 * @author 洪锦辉
 * 2022/3/3
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendSeckillMessage(String message) {
        log.info(message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
    }
}
