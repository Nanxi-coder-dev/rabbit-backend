package com.rabbit.service;

import com.rabbit.vo.CategoryHeadVO;
import com.rabbit.vo.CategoryVO;
import com.rabbit.vo.SubCategoryVO;

import java.util.List;

public interface CategoryService {
    /** 首页分类导航（顶级 + 二级 + 推荐商品） */
    List<CategoryHeadVO> headList();

    /** 分类页详情 */
    CategoryVO detail(Long id);

    /** 面包屑：二级分类 + 它的父分类名 */
    SubCategoryVO subFilter(Long id);
}