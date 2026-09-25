package com.rabbit.service;

import com.rabbit.service.GoodsService;
import com.rabbit.vo.GoodsDetailVO;
import com.rabbit.vo.GoodsSimpleVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    // TODO: 等 D 交付 GoodsMapper / GoodsSkuMapper / CategoryMapper 后注入

    @Override
    public GoodsDetailVO getDetail(Long id) {
        // TODO: 等 D 交付 Goods / GoodsSku / Category 实体和 Mapper 后实现
        throw new UnsupportedOperationException("待 D 交付后实现：商品详情组装");
    }

    @Override
    public List<GoodsSimpleVO> getHotGoods(Long id, Integer type, Integer limit) {
        // TODO: 等 D 交付 GoodsMapper 后实现
        throw new UnsupportedOperationException("待 D 交付后实现：热榜商品查询");
    }

    @Override
    public List<GoodsSimpleVO> getRelevant(Integer limit) {
        // TODO: 等 D 交付 GoodsMapper 后实现
        throw new UnsupportedOperationException("待 D 交付后实现：相关推荐查询");
    }
}