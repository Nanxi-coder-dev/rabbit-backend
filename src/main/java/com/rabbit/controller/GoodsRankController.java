package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.GoodsRankService;
import com.rabbit.vo.GoodsItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 热榜 + 相关推荐接口（成员 C）。
 * 前端调用：
 *   GET /goods/hot?id&type&limit      → 商品详情页右侧热榜
 *   GET /goods/relevant?limit         → 商品详情页底部相关推荐
 * 两条都已在 A 的 WebConfig 白名单中，不需要 token。
 *
 * ⚠️ 与 E（feature/goods-detail）的 GoodsController 中 /goods/hot、/goods/relevant
 * 路径重叠：两个版本契约完全一致（参数、返回字段），但 Spring 不允许同名映射
 * 同时存在，联调合入时二选一，详见《成员C 完成说明.txt》第八节。
 * 本类不改 E 的任何文件。
 */
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsRankController {

    private final GoodsRankService goodsRankService;

    /**
     * 热榜商品。
     * id     当前商品ID，传入则将其排除（详情页不看自己）
     * type   榜单类型（设计稿注明语义待产品确认，当前版本统一按销量榜简化）
     * limit  条数，默认 3
     */
    @GetMapping("/hot")
    public Result<List<GoodsItemVO>> hot(@RequestParam(required = false) Long id,
                                         @RequestParam(required = false) Integer type,
                                         @RequestParam(required = false) Integer limit) {
        return Result.success(goodsRankService.hot(id, type, limit));
    }

    /**
     * 相关推荐。
     * limit  条数，默认 4
     */
    @GetMapping("/relevant")
    public Result<List<GoodsItemVO>> relevant(@RequestParam(required = false) Integer limit) {
        return Result.success(goodsRankService.relevant(limit));
    }
}
