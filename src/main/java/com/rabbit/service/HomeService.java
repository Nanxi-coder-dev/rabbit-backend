package com.rabbit.service;

import com.rabbit.vo.*;
import java.util.List;

public interface HomeService {
    List<HomeBannerVO> getBanner(Integer distributionSite);
    List<GoodsSimpleVO> getNewGoods();
    List<HomeHotGoodsVO> getHotGoods();
    List<HomeGoodsVO> getHomeGoods();
    List<HomeCategoryHeadVO> getCategoryHead();
}