package com.rabbit.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品分页结果 VO（POST /category/goods/temporary 的 result）。
 * 前端只读 result.items 与 result.counts，字段名必须与设计稿一致。
 */
@Data
public class GoodsPageVO {

    /** 当前页商品列表 */
    private List<GoodsItemVO> items;

    /** 符合条件的商品总数（用于前端计算总页数） */
    private Long counts;

    /** 当前页码（回显） */
    private Integer page;

    /** 每页条数（回显） */
    private Integer pageSize;
}
