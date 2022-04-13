package com.hjh.service;

import com.hjh.vo.GoodsVo;

import java.util.List;

/**
 * @author 洪锦辉
 * 2022/2/17
 */

public interface GoodsService {
    List<GoodsVo> getAllGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Long goodsId);
}
