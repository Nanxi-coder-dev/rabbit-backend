package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.GoodsService;
import com.rabbit.vo.GoodsDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
}