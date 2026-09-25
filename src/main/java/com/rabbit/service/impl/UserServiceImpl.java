package com.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.common.BizException;
import com.rabbit.dto.LoginDTO;
import com.rabbit.entity.User;
import com.rabbit.mapper.UserMapper;
import com.rabbit.service.UserService;
import com.rabbit.util.JwtUtil;
import com.rabbit.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户服务实现
 * 成员 D 负责（登录模块）
 *
 * 依赖 A 成员交付的基础类：
 *   - BizException       业务异常
 *   - JwtUtil            JWT 生成工具
 *   - GlobalExceptionHandler  全局异常处理（自动将 BizException 转为非 2xx 响应）
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    /** BCrypt 密码编码器（spring-security-crypto） */
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public LoginVO login(LoginDTO dto) {
        // 1. 根据账号查询用户
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getAccount, dto.getAccount())
        );

        // 2. 用户不存在
        if (user == null) {
            throw new BizException("账号或密码错误");
        }

        // 3. 账号被禁用
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BizException("账号已被禁用，请联系管理员");
        }

        // 4. BCrypt 校验密码（种子数据中密码 123456 的 BCrypt 哈希）
        if (!PASSWORD_ENCODER.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException("账号或密码错误");
        }

        // 5. 生成 JWT Token（A 成员 JwtUtil 约定：createToken(Map.of("userId", id))）
        String token = jwtUtil.createToken(Map.of("userId", user.getId()));

        // 6. 组装返回 VO（前端将 result 整体存入 userInfo 并持久化）
        LoginVO vo = new LoginVO();
        vo.setId(user.getId());
        vo.setAccount(user.getAccount());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setToken(token);

        return vo;
    }
}