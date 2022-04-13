package com.hjh.mapper;

import com.hjh.pojo.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 洪锦辉
 * 2022/2/18
 */
@Mapper
public interface SeckillGoodsMapper {
    Integer reduceStockById(Long id, Integer stockCount);
    SeckillGoods getSeckillGoodsByGoodsId(Long goodsId);
}
