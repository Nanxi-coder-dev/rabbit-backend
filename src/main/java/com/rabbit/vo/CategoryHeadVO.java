package com.rabbit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 首页分类导航 VO（GET /home/category/head 的数组元素）。
 * children = 二级分类；goods = 推荐商品。
 */
@Data
public class CategoryHeadVO {
    private Long id;
    private String name;
    private List<ChildVO> children;
    private List<GoodsItemVO> goods;

    @Data
    public static class ChildVO {
        private Long id;
        private String name;
    }

    @Data
    public static class GoodsItemVO {
        private Long id;
        private String name;
        private String desc;
        private String picture;
        private BigDecimal price;
    }
}