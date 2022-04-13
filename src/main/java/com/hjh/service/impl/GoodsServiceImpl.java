package com.hjh.service.impl;

import com.hjh.mapper.GoodsMapper;
import com.hjh.service.GoodsService;
import com.hjh.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 洪锦辉
 * 2022/2/17
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> getAllGoodsVo() {
        return goodsMapper.getAllGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }
}
