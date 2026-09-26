package com.rabbit.service;

import com.rabbit.dto.GoodsPageQueryDTO;
import com.rabbit.vo.GoodsPageVO;

/**
 * 分类商品分页（成员 C）。
 */
public interface CategoryGoodsService {

    /**
     * 分页查询分类下商品，支持排序。
     *
     * @param query categoryId（必填）、page、pageSize、sortField
     * @return 分页结果 {items, counts, page, pageSize}
     */
    GoodsPageVO page(GoodsPageQueryDTO query);
}
