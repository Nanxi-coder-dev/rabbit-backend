package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.GoodsService;
import com.rabbit.vo.GoodsDetailVO;
import com.rabbit.vo.GoodsSimpleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    /** 商品详情 */
    @GetMapping
    public Result<GoodsDetailVO> detail(@RequestParam Long id) {
        return Result.success(goodsService.getDetail(id));
    }

    /** 热榜商品 */
    @GetMapping("/hot")
    public Result<List<GoodsSimpleVO>> hot(@RequestParam(required = false) Long id,
                                           @RequestParam(required = false) Integer type,
                                           @RequestParam(required = false, defaultValue = "3") Integer limit) {
        return Result.success(goodsService.getHotGoods(id, type, limit));
    }

    /** 相关推荐 */
    @GetMapping("/relevant")
    public Result<List<GoodsSimpleVO>> relevant(@RequestParam(required = false, defaultValue = "4") Integer limit) {
        return Result.success(goodsService.getRelevant(limit));
    }
}