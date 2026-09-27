package com.rabbit.common;

import lombok.Getter;

/**
 * 状态码枚举。
 * 前端不读 code，只读 message，所以这里主要是给后端统一文案用。
 */
@Getter
public enum ResultCode {
    SUCCESS("1", "ok"),
    FAIL("0", "操作失败"),
    UNAUTHORIZED("0", "未登录或 token 已过期"),
    LOGIN_FAIL("0", "账号或密码错误"),
    PARAM_ERROR("0", "参数错误");

    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}