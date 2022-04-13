package com.hjh.vo;

import com.hjh.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 洪锦辉
 * 2022/2/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsVo extends Goods {
    private BigDecimal seckilPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}
