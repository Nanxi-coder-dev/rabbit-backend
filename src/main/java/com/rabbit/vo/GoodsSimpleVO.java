package com.rabbit.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsSimpleVO {

    private Long id;
    private String name;
    private String desc;
    private String picture;
    private BigDecimal price;
}