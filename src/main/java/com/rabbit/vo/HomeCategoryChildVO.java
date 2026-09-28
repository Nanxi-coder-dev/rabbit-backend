package com.rabbit.vo;

import lombok.Data;
import java.util.List;

@Data
public class HomeCategoryChildVO {
    private Long id;
    private String name;
    private String picture;
    private List<GoodsSimpleVO> goods;
}