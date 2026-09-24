package com.rabbit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分类页 VO（GET /category?id 的返回）。
 * children 里每个二级分类带自己的推荐商品 goods。
 */
@Data
public class CategoryVO {
    private Long id;
    private String name;
    private List<ChildVO> children;

    @Data
    public static class ChildVO {
        private Long id;
        private String name;
        private String picture;
        private List<GoodsItemVO> goods;
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