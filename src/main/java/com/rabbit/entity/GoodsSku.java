package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品SKU实体
 * 成员 D 负责
 * specs 字段与 goods.specs 严格对齐
 */
@Data
@TableName("goods_sku")
public class GoodsSku {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 商品ID */
    private Long goodsId;

    /** 价格 */
    private BigDecimal price;

    /** 原价 */
    private BigDecimal oldPrice;

    /** 库存 */
    private Integer inventory;

    /** SKU图片 */
    private String picture;

    /** SKU规格（JSON数组，与goods.specs对齐） */
    private String specs;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}