package com.rabbit.dto;

import lombok.Data;

/**
 * 登录请求参数。
 * 对应 POST /login 的 body：{account, password}
 */
@Data
public class LoginDTO {
    private String account;
    private String password;
}