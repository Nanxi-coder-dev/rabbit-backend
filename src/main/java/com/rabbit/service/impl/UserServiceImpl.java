package com.rabbit.service.impl;

import com.rabbit.dto.LoginDTO;
import com.rabbit.service.UserService;
import com.rabbit.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // TODO: 等 D 交付 UserMapper 后取消注释
    // private final UserMapper userMapper;
    // private final JwtUtil jwtUtil;

    @Override
    public LoginVO login(LoginDTO dto) {
        // TODO: 等 D 交付 user 表 + UserMapper 后实现
        // 1. 按 account 查 user
        // 2. BCrypt 校验密码（失败 throw new BizException(ResultCode.LOGIN_FAIL)）
        // 3. jwtUtil.createToken(Map.of("userId", user.getId()))
        // 4. 组装 LoginVO 返回
        return null;
    }
}