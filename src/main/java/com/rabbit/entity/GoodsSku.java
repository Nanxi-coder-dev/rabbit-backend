package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品SKU实体
 * 成员 D 负责
 */
@Data
@TableName("goods_sku")
public class GoodsSku {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 商品ID */
    private Long goodsId;

    /** SKU编码 */
    private String skuCode;

    /** 价格 */
    private BigDecimal price;

    /** 原价 */
    private BigDecimal oldPrice;

    /** 库存 */
    private Integer inventory;

    /** SKU图片 */
    private String picture;

    /** SKU规格JSON */
    private String specs;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}