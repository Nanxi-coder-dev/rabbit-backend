package com.rabbit.service;

import com.rabbit.vo.GoodsDetailVO;


public interface GoodsService {

    /** 商品详情 */
    GoodsDetailVO getDetail(Long id);
}