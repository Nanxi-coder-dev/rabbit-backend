package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 * 成员 D 负责
 * 字段对齐小兔鲜黑马真实接口数据
 */
@Data
@TableName("goods")
public class Goods {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 商品名称 */
    private String name;

    /** 商品描述（desc为SQL关键字，用反引号） */
    @TableField("`desc`")
    private String desc;

    /** 销售价 */
    private BigDecimal price;

    /** 原价 */
    private BigDecimal oldPrice;

    /** 商品主图（单张，列表用） */
    private String picture;

    /** 商品图片列表（JSON数组，详情用） */
    private String mainPictures;

    /** 商品规格（JSON数组） */
    private String specs;

    /** 库存 */
    private Integer inventory;

    /** 销量 */
    private Integer salesCount;

    /** 排序序号 */
    private Integer orderNum;

    /** 二级分类ID */
    private Long categoryId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}