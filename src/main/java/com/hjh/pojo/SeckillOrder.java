package com.hjh.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Data
@ToString
public class SeckillOrder {
    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;
}
