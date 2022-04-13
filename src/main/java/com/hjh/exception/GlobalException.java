package com.hjh.exception;

import com.hjh.vo.RespBean;
import com.hjh.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 全局异常
 *
 * @author 洪锦辉
 * 2022/2/16
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;
}
