package com.hjh.mapper;

import com.hjh.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 洪锦辉
 * 2022/2/17
 */
@Mapper
public interface GoodsMapper {
    List<GoodsVo> getAllGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Long goodsId);
}
