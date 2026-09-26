package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 * 成员 D 负责
 * 字段对齐小兔鲜黑马真实接口数据 + 设计稿 _design_extract.txt
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

    /** 销量 */
    private Integer salesCount;

    /** 评论数 */
    private Integer commentCount;

    /** 收藏数 */
    private Integer collectCount;

    /** 品牌名 */
    private String brandName;

    /** 详情属性（JSON数组） */
    private String detailsProperties;

    /** 详情图（JSON数组） */
    private String detailsPictures;

    /** 商品规格（JSON数组） */
    private String specs;

    /** 二级分类ID */
    private Long categoryId;

    /** 发布时间 */
    private LocalDateTime publishTime;

    /** 是否新品：0-否，1-是 */
    private Integer isNew;

    /** 排序序号 */
    private Integer orderNum;

    /** 评价数 */
    private Integer evaluateNum;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}