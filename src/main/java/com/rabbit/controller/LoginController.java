package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.dto.LoginDTO;
import com.rabbit.service.UserService;
import com.rabbit.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 登录控制器
 * 成员 D 负责（登录模块）
 *
 * 接口：POST /login
 * 请求体：{ "account": "admin", "password": "123456" }
 * 响应：{ "code": "1", "message": "ok", "result": { id, account, nickname, avatar, token } }
 *
 * 注意：/login 已在 A 成员的 WebConfig 拦截器白名单中，不需要 token
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        return Result.success(userService.login(dto));
    }
}