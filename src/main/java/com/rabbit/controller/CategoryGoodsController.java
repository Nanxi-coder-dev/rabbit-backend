package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.dto.GoodsPageQueryDTO;
import com.rabbit.service.CategoryGoodsService;
import com.rabbit.vo.GoodsPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类下商品分页接口（成员 C）。
 * 前端调用：POST /category/goods/temporary（商品列表页，分页 + 排序）
 * 已在 A 的 WebConfig 白名单中（/category/goods/temporary），不需要 token。
 *
 * 注意：这里不改动 A 的 CategoryController，单独建 Controller，
 * Spring 允许多个 Controller 共用 /category 前缀，只要方法路径不重复即可。
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryGoodsController {

    private final CategoryGoodsService categoryGoodsService;

    /**
     * 商品分页列表。
     * body: {categoryId, page, pageSize, sortField}
     * result: {items:[{id,name,desc,picture,price}], counts, page, pageSize}
     */
    @PostMapping("/goods/temporary")
    public Result<GoodsPageVO> page(@RequestBody GoodsPageQueryDTO query) {
        return Result.success(categoryGoodsService.page(query));
    }
}
