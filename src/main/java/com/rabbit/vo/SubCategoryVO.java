package com.rabbit.vo;

import lombok.Data;

/**
 * 面包屑 VO（GET /category/sub/filter?id 的返回）。
 * 前端用它渲染 "一级 > 二级" 的导航。
 */
@Data
public class SubCategoryVO {
    private Long id;
    private String name;
    private Long parentId;
    private String parentName;
}