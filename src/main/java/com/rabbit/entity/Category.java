package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类实体
 * 成员 D 负责
 * 字段对齐设计稿: id, name, parent_id, picture, sale_info, sort
 */
@Data
@TableName("category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分类名称 */
    private String name;

    /** 父分类ID，0表示一级分类 */
    private Long parentId;

    /** 分类图片URL */
    private String picture;

    /** 营销标语 */
    private String saleInfo;

    /** 排序号 */
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}