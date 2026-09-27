package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.HomeService;
import com.rabbit.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    /**
     * 轮播图
     * distributionSite: 1=首页，2=商品页，默认 1
     */
    @GetMapping("/banner")
    public Result<List<HomeBannerVO>> banner(
            @RequestParam(defaultValue = "1") Integer distributionSite) {
        return Result.success(homeService.getBanner(distributionSite));
    }

    /**
     * 新鲜好物
     */
    @GetMapping("/new")
    public Result<List<GoodsSimpleVO>> newGoods() {
        return Result.success(homeService.getNewGoods());
    }

    /**
     * 人气推荐
     */
    @GetMapping("/hot")
    public Result<List<HomeHotGoodsVO>> hotGoods() {
        return Result.success(homeService.getHotGoods());
    }

    /**
     * 首页所有商品模块
     */
    @GetMapping("/goods")
    public Result<List<HomeGoodsVO>> homeGoods() {
        return Result.success(homeService.getHomeGoods());
    }

    /**
     * 首页分类导航
     * （注意：如果 A 成员已经写了这个接口，请把这里注释掉，避免路径冲突）
     */
    @GetMapping("/category/head")
    public Result<List<HomeCategoryHeadVO>> categoryHead() {
        return Result.success(homeService.getCategoryHead());
    }
}