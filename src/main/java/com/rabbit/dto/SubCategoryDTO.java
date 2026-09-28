package com.rabbit.dto;

import lombok.Data;

/**
 * 面包屑查询参数。
 * 对应 GET /category/sub/filter?id=xx
 */
@Data
public class SubCategoryDTO {
    private Long id;
}