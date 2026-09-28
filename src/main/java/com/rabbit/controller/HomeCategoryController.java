package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.HomeService;
import com.rabbit.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页分类导航。
 * 前端调用：GET /home/category/head
 * 路径以 /home 开头，已在 WebConfig 白名单里，不需要 token。
 * 注意：路径和 CategoryController 的 /category 不同，
 * 所以单独建一个 Controller，避免 @RequestMapping 前缀冲突。
 */
@RestController
@RequiredArgsConstructor
public class HomeCategoryController {

    private final HomeService homeService;

    /**
     * 轮播图
     * distributionSite: 1=首页，2=商品页，默认 1
     */
    @GetMapping("/home/banner")
    public Result<List<HomeBannerVO>> banner(
            @RequestParam(defaultValue = "1") Integer distributionSite) {
        return Result.success(homeService.getBanner(distributionSite));
    }

    /**
     * 新鲜好物
     */
    @GetMapping("/home/new")
    public Result<List<GoodsSimpleVO>> newGoods() {
        return Result.success(homeService.getNewGoods());
    }

    /**
     * 人气推荐
     */
    @GetMapping("/home/hot")
    public Result<List<HomeHotGoodsVO>> hotGoods() {
        return Result.success(homeService.getHotGoods());
    }

    /**
     * 首页所有商品模块
     */
    @GetMapping("/home/goods")
    public Result<List<HomeGoodsVO>> homeGoods() {
        return Result.success(homeService.getHomeGoods());
    }

    /**
     * 首页分类导航
     * （注意：此为B成员部分，A成员越界已删）
     */
    @GetMapping("/home/category/head")
    public Result<List<HomeCategoryHeadVO>> categoryHead() {
        return Result.success(homeService.getCategoryHead());
    }
}