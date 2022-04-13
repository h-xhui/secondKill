package com.hjh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 洪锦辉
 * 2022/2/15
 */
public enum RespBeanEnum {
    // 通用
    SUCCESS(200, "success"),
    ERROR(500, "服务端异常"),
    INVALID_PARAM(500101, "参数校验失败"),

    // 登录
    EMPTY_PARAM(500201, "参数为空"),
    WRONG_PASSWORD(500202, "密码错误"),
    PHONE_ERROR(500203, "手机号格式错误"),
    PHONE_NOT_FIND(500204, "手机号不存在"),
    SESSION_ERROR(5600205, "用户为登录"),

    // 秒杀
    STOCK_EMPTY(500301, "库存为空"),
    REPEAT_BUY(500302, "重复购买"),
    REQUEST_ILLEGAL(500303, "请求非法，请重新尝试");

    private Integer code;
    private String message;
    private RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
