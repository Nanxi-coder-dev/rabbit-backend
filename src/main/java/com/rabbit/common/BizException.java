package com.rabbit.common;

import lombok.Getter;

/**
 * 业务异常。
 * Service 层校验不通过时直接 throw，
 * GlobalExceptionHandler 会捕获并转成 401 + 统一 JSON。
 */
@Getter
public class BizException extends RuntimeException {

    private final String message;

    public BizException(String message) {
        super(message);
        this.message = message;
    }

    public BizException(ResultCode rc) {
        super(rc.getMessage());
        this.message = rc.getMessage();
    }
}