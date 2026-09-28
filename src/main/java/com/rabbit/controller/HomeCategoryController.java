package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.service.CategoryService;
import com.rabbit.vo.CategoryHeadVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页分类导航。
 * 前端调用：GET /home/category/head
 * 路径以 /home 开头，已在 WebConfig 白名单里，不需要 token。
 *
 * 注意：路径和 CategoryController 的 /category 不同，
 * 所以单独建一个 Controller，避免 @RequestMapping 前缀冲突。
 */
@RestController
@RequiredArgsConstructor
public class HomeCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/home/category/head")
    public Result<List<CategoryHeadVO>> head() {
        return Result.success(categoryService.headList());
    }
}