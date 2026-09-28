package com.rabbit.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品卡片 VO。
 * 字段契约 {id, name, desc, picture, price}，与设计稿 4.3 / 4.4 完全一致，
 * 商品列表、热榜、相关推荐三处共用，避免每个 Controller 重复造内部类。
 */
@Data
public class GoodsItemVO {

    private Long id;
    private String name;
    private String desc;
    private String picture;
    private BigDecimal price;
}
