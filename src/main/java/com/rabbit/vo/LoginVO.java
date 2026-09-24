package com.rabbit.vo;

import lombok.Data;

/**
 * 登录返回。
 * 前端必须能从 result 里拿到 token 字段（见 src/utils/http.js:78）。
 */
@Data
public class LoginVO {
    private Long id;
    private String account;
    private String avatar;
    private String nickname;
    private String token;
}