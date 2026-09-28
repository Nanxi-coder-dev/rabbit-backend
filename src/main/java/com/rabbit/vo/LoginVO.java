package com.rabbit.vo;

import lombok.Data;

/**
 * 登录返回 VO
 * 成员 D 负责（登录模块）
 * 前端将 result 整体存入 userInfo 并持久化，token 用于后续请求鉴权
 */
@Data
public class LoginVO {

    private Long id;

    private String account;

    private String nickname;

    private String avatar;

    /** JWT Token，前端存入请求头 Authorization: Bearer <token> */
    private String token;
}