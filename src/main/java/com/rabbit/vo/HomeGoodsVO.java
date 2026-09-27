package com.rabbit.vo;

import lombok.Data;
import java.util.List;

@Data
public class HomeGoodsVO {
    private Long id;
    private String name;
    private String picture;
    private String saleInfo;
    private List<GoodsSimpleVO> goods;
}