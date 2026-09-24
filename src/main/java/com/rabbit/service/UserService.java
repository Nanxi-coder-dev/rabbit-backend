package com.rabbit.service;

import com.rabbit.dto.LoginDTO;
import com.rabbit.vo.LoginVO;

public interface UserService {
    /** 登录：校验账号密码，签发 JWT */
    LoginVO login(LoginDTO dto);
}