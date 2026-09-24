package com.rabbit.service.impl;

import com.rabbit.service.CategoryService;
import com.rabbit.vo.CategoryHeadVO;
import com.rabbit.vo.CategoryVO;
import com.rabbit.vo.SubCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    // TODO: 等 D 交付后取消注释
    // private final CategoryMapper categoryMapper;
    // private final GoodsMapper goodsMapper;

    @Override
    public List<CategoryHeadVO> headList() {
        // TODO: 查 parent_id=0 的顶级分类，每个挂 children + 推荐商品
        return null;
    }

    @Override
    public CategoryVO detail(Long id) {
        // TODO: 查顶级分类 + 它的二级分类 + 每个二级的推荐商品
        return null;
    }

    @Override
    public SubCategoryVO subFilter(Long id) {
        // TODO: 查二级分类 + 它的父分类名
        return null;
    }
}