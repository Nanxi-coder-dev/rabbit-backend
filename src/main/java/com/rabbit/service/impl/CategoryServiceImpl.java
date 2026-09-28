package com.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.common.BizException;
import com.rabbit.entity.Category;
import com.rabbit.entity.Goods;
import com.rabbit.mapper.CategoryMapper;
import com.rabbit.mapper.GoodsMapper;
import com.rabbit.service.CategoryService;
import com.rabbit.vo.CategoryHeadVO;
import com.rabbit.vo.CategoryVO;
import com.rabbit.vo.SubCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类业务实现。
 * 数据表来自 D：category(parentId=0 顶级) + goods(categoryId 指向二级分类)
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final GoodsMapper goodsMapper;

    /** 每个顶级分类下推荐多少商品 */
    private static final int GOODS_LIMIT_PER_TOP = 4;

    @Override
    public List<CategoryHeadVO> headList() {
        List<Category> tops = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getParentId, 0L)
                        .orderByAsc(Category::getSort));

        List<CategoryHeadVO> result = new ArrayList<>();
        for (Category top : tops) {
            CategoryHeadVO vo = new CategoryHeadVO();
            vo.setId(top.getId());
            vo.setName(top.getName());

            List<Category> subs = categoryMapper.selectList(
                    new LambdaQueryWrapper<Category>()
                            .eq(Category::getParentId, top.getId())
                            .orderByAsc(Category::getSort));

            vo.setChildren(subs.stream().map(s -> {
                CategoryHeadVO.ChildVO c = new CategoryHeadVO.ChildVO();
                c.setId(s.getId());
                c.setName(s.getName());
                return c;
            }).collect(Collectors.toList()));

            List<Long> subIds = subs.stream().map(Category::getId).collect(Collectors.toList());
            if (!subIds.isEmpty()) {
                List<Goods> goodsList = goodsMapper.selectList(
                        new LambdaQueryWrapper<Goods>()
                                .in(Goods::getCategoryId, subIds)
                                .orderByDesc(Goods::getSalesCount)
                                .last("LIMIT " + GOODS_LIMIT_PER_TOP));
                vo.setGoods(goodsList.stream().map(this::toGoodsItem).collect(Collectors.toList()));
            } else {
                vo.setGoods(new ArrayList<>());
            }
            result.add(vo);
        }
        return result;
    }

    @Override
    public CategoryVO detail(Long id) {
        Category top = categoryMapper.selectById(id);
        if (top == null) {
            throw new BizException("分类不存在");
        }

        CategoryVO vo = new CategoryVO();
        vo.setId(top.getId());
        vo.setName(top.getName());

        List<Category> subs = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getParentId, id)
                        .orderByAsc(Category::getSort));

        vo.setChildren(subs.stream().map(s -> {
            CategoryVO.ChildVO c = new CategoryVO.ChildVO();
            c.setId(s.getId());
            c.setName(s.getName());
            c.setPicture(s.getPicture());

            List<Goods> gs = goodsMapper.selectList(
                    new LambdaQueryWrapper<Goods>()
                            .eq(Goods::getCategoryId, s.getId())
                            .orderByDesc(Goods::getSalesCount)
                            .last("LIMIT " + GOODS_LIMIT_PER_TOP));
            c.setGoods(gs.stream().map(this::toCatGoodsItem).collect(Collectors.toList()));
            return c;
        }).collect(Collectors.toList()));

        return vo;
    }

    @Override
    public SubCategoryVO subFilter(Long id) {
        Category sub = categoryMapper.selectById(id);
        if (sub == null) {
            throw new BizException("分类不存在");
        }

        SubCategoryVO vo = new SubCategoryVO();
        vo.setId(sub.getId());
        vo.setName(sub.getName());
        vo.setParentId(sub.getParentId());

        Category parent = categoryMapper.selectById(sub.getParentId());
        vo.setParentName(parent == null ? "" : parent.getName());
        return vo;
    }

    private CategoryHeadVO.GoodsItemVO toGoodsItem(Goods g) {
        CategoryHeadVO.GoodsItemVO v = new CategoryHeadVO.GoodsItemVO();
        v.setId(g.getId());
        v.setName(g.getName());
        v.setDesc(g.getDesc());
        v.setPrice(g.getPrice());
        v.setPicture(resolvePicture(g));
        return v;
    }

    private CategoryVO.GoodsItemVO toCatGoodsItem(Goods g) {
        CategoryVO.GoodsItemVO v = new CategoryVO.GoodsItemVO();
        v.setId(g.getId());
        v.setName(g.getName());
        v.setDesc(g.getDesc());
        v.setPrice(g.getPrice());
        v.setPicture(resolvePicture(g));
        return v;
    }

    private String resolvePicture(Goods g) {
        if (g.getPicture() != null && !g.getPicture().isBlank()) {
            return g.getPicture();
        }
        return firstPicture(g.getMainPictures());
    }

    private String firstPicture(String mainPictures) {
        if (mainPictures == null || mainPictures.isBlank()) return "";
        String s = mainPictures.trim();
        if (s.startsWith("[")) s = s.substring(1);
        if (s.endsWith("]")) s = s.substring(0, s.length() - 1);
        String[] parts = s.split(",");
        if (parts.length == 0) return "";
        String first = parts[0].trim();
        if (first.startsWith("\"")) first = first.substring(1);
        if (first.endsWith("\"")) first = first.substring(0, first.length() - 1);
        return first;
    }
}