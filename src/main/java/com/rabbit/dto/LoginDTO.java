package com.rabbit.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 登录请求 DTO
 * 成员 D 负责（登录模块）
 */
@Data
public class LoginDTO {

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}