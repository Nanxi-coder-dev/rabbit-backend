package com.rabbit.controller;

import com.rabbit.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 购物车桩（登录流程依赖）。
 * 前端只要求：
 *   GET  /member/cart        → result: []
 *   POST /member/cart/merge  → result: {}
 * 这两条需要 token，拦截器会校验（未登录返回 401）。
 * 真实购物车逻辑不在本阶段范围内。
 */
@RestController
@RequestMapping("/member/cart")
public class CartController {

    @GetMapping
    public Result<Object> list(HttpServletRequest request) {
        // userId 已由 JwtInterceptor 挂到 request 上
        Long userId = (Long) request.getAttribute("userId");
        // 桩：暂时返回空数组
        return Result.success(new ArrayList<>());
    }

    @PostMapping("/merge")
    public Result<Object> merge(@RequestBody Object body) {
        // 桩：合并购物车，先返回空对象
        return Result.success(new HashMap<>());
    }
}