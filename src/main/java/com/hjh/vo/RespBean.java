package com.hjh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 洪锦辉
 * 2022/2/15
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private Integer code;
    private String message;
    private Object result;

    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    public static RespBean success(Object result) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), result);
    }

    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum, Object result) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), result);
    }
}
