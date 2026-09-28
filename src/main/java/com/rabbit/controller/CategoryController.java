package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.CategoryService;
import com.rabbit.vo.CategoryVO;
import com.rabbit.vo.SubCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 分类相关接口。
 * 前端调用：
 *   GET /category?id=xx            → 分类页详情
 *   GET /category/sub/filter?id=xx → 面包屑
 * 两条都在白名单，不需要 token。
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Result<CategoryVO> detail(@RequestParam Long id) {
        return Result.success(categoryService.detail(id));
    }

    @GetMapping("/sub/filter")
    public Result<SubCategoryVO> subFilter(@RequestParam Long id) {
        return Result.success(categoryService.subFilter(id));
    }
}