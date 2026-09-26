package com.rabbit.service;

import com.rabbit.vo.GoodsItemVO;

import java.util.List;

/**
 * 热榜 + 相关推荐（成员 C）。
 */
public interface GoodsRankService {

    /**
     * 热榜商品（按销量取 top-N）。
     *
     * @param id    当前商品ID，传入则排除（详情页场景）
     * @param type  榜单类型（设计稿注明语义待确认，当前版本按销量榜简化，不影响结果）
     * @param limit 条数，默认 3
     */
    List<GoodsItemVO> hot(Long id, Integer type, Integer limit);

    /**
     * 相关推荐（全站热销 top-N，设计稿无更多筛选参数）。
     *
     * @param limit 条数，默认 4
     */
    List<GoodsItemVO> relevant(Integer limit);
}
