package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 轮播图实体
 * 成员 D 负责
 * 字段对齐前端: imgUrl, hrefUrl
 */
@Data
@TableName("banner")
public class Banner {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 图片URL */
    private String imgUrl;

    /** 跳转链接 */
    private String hrefUrl;

    /** 投放位置 1首页 2商品页 */
    private Integer distributionSite;

    /** 排序号 */
    private Integer sort;

    /** 状态 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}