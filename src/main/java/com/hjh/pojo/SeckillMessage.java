package com.hjh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 秒杀消息
 *
 * @author 洪锦辉
 * 2022/3/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeckillMessage {
    private User user;
    private Long goodsId;
}
