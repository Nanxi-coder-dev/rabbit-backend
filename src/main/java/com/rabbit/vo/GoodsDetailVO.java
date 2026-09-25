package com.rabbit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsDetailVO {

    private Long id;
    private String name;
    private String desc;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private List<String> mainPictures;

    /** [0]=二级分类 [1]=一级分类，顺序不能反 */
    private List<CategoryVO> categories;

    private Integer salesCount;
    private Integer commentCount;
    private Integer collectCount;

    private BrandVO brand;
    private DetailsVO details;
    private List<SpecVO> specs;
    private List<SkuVO> skus;

    // ---------- 内部类 ----------

    @Data
    public static class CategoryVO {
        private Long id;
        private String name;
    }

    @Data
    public static class BrandVO {
        private String name;
    }

    @Data
    public static class DetailsVO {
        private List<PropertyVO> properties;
        private List<String> pictures;
    }

    @Data
    public static class PropertyVO {
        private String name;
        private String value;
    }

    @Data
    public static class SpecVO {
        private Long id;
        private String name;
        private List<SpecValueVO> values;
    }

    @Data
    public static class SpecValueVO {
        private String name;
        private String picture;
    }

    @Data
    public static class SkuVO {
        private Long id;
        private BigDecimal price;
        private BigDecimal oldPrice;
        private Integer inventory;
        private String picture;
        private List<SkuSpecVO> specs;
    }

    @Data
    public static class SkuSpecVO {
        private String name;
        private String valueName;
    }
}