package com.rabbit.service;

import com.rabbit.vo.GoodsDetailVO;
import com.rabbit.vo.GoodsSimpleVO;

import java.util.List;


public interface GoodsService {

    /** 商品详情 */
    GoodsDetailVO getDetail(Long id);

    /** 热榜商品 */
    List<GoodsSimpleVO> getHotGoods(Long id, Integer type, Integer limit);

    /** 相关推荐 */
    List<GoodsSimpleVO> getRelevant(Integer limit);
}