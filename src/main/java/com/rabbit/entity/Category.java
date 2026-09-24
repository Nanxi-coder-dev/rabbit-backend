package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类实体
 * 成员 D 负责
 * 字段对齐 A 成员需求: id, name, parent_id, picture, sale_info, sort
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

    /** 层级 1一级 2二级 */
    private Integer level;

    /** 分类图片URL */
    private String picture;

    /** 营销标语 */
    private String saleInfo;

    /** 排序号 */
    private Integer sort;

    /** 状态 0禁用 1启用 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}