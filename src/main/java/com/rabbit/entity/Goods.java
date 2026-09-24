package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 * 成员 D 负责
 * 基础字段对齐 A: id, name, `desc`, price, main_pictures, category_id
 * 扩展字段供 E（商品详情）使用
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

    /** 主图JSON数组 */
    private String mainPictures;

    /** 二级分类ID */
    private Long categoryId;

    /** 品牌名称 */
    private String brandName;

    /** 品牌Logo */
    private String brandLogo;

    /** 商品规格JSON */
    private String specs;

    /** 详情图JSON数组 */
    private String details;

    /** 销量 */
    private Integer salesCount;

    /** 评论数 */
    private Integer commentCount;

    /** 收藏数 */
    private Integer collectCount;

    /** 评价数 */
    private Integer evaluateNum;

    /** 订单数 */
    private Integer orderNum;

    /** 发布时间 */
    private LocalDateTime publishTime;

    /** 是否新品 */
    private Integer isNew;

    /** 状态 0下架 1上架 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}