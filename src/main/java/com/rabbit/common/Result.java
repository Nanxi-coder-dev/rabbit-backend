package com.rabbit.common;

import lombok.Data;

/**
 * 统一返回体。
 *
 * 成功：HTTP 200 + {"code":"1","message":"ok","result":{...}}
 * 失败：非 2xx   + {"code":"0","message":"错误信息"}
 *
 * 前端只读 res.result 和 e.response.data.message，
 * 所以 result / message 字段名必须保持一致，code 可自由取值。
 */
@Data
public class Result<T> {

    private String code;
    private String message;
    private T result;

    /** 成功，携带数据 */
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode("1");
        r.setMessage("ok");
        r.setResult(data);
        return r;
    }

    /** 成功，无数据（如删除、购物车桩） */
    public static <T> Result<T> success() {
        return success(null);
    }

    /** 失败，由 GlobalExceptionHandler 或手动调用 */
    public static <T> Result<T> fail(String message) {
        Result<T> r = new Result<>();
        r.setCode("0");
        r.setMessage(message);
        return r;
    }

    public static <T> Result<T> fail(ResultCode rc) {
        return fail(rc.getMessage());
    }
}