package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 轮播图实体
 * 成员 D 负责
 * 字段对齐设计稿: id, img_url, href_url, distribution_site
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

    /** 投放位置：1-首页，2-商品页 */
    private Integer distributionSite;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}