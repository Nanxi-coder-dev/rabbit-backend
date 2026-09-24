package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("goods_sku")
public class GoodsSku {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long goodsId;

    private BigDecimal price;

    private BigDecimal oldPrice;

    private Integer inventory;

    /** JSON 列：[{"name":"颜色","valueName":"黑色"}] */
    private String specs;
}