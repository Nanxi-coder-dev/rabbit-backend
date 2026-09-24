package com.rabbit.service;

import com.rabbit.dto.LoginDTO;
import com.rabbit.vo.LoginVO;

/**
 * 用户服务接口
 * 成员 D 负责（登录模块）
 */
public interface UserService {

    /**
     * 用户登录
     * @param dto 登录请求（account + password）
     * @return 登录成功返回用户信息 + token
     */
    LoginVO login(LoginDTO dto);
}