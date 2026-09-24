package com.rabbit.controller;

import com.rabbit.common.Result;
import com.rabbit.dto.LoginDTO;
import com.rabbit.service.UserService;
import com.rabbit.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口。
 * 前端调用：POST /login  body: {account, password}
 * 白名单已放行（见 WebConfig），不需要 token。
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        return Result.success(userService.login(dto));
    }
}