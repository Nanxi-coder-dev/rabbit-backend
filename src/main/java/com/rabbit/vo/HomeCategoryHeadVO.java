package com.rabbit.vo;

import lombok.Data;
import java.util.List;

@Data
public class HomeCategoryHeadVO {
    private Long id;
    private String name;
    private List<HomeCategoryChildVO> children;
    private List<GoodsSimpleVO> goods;
}