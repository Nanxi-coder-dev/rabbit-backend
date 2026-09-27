package com.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbit.entity.Banner;
import com.rabbit.entity.Category;
import com.rabbit.entity.Goods;
import com.rabbit.mapper.BannerMapper;
import com.rabbit.mapper.CategoryMapper;
import com.rabbit.mapper.GoodsMapper;
import com.rabbit.service.HomeService;
import com.rabbit.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final BannerMapper bannerMapper;
    private final CategoryMapper categoryMapper;
    private final GoodsMapper goodsMapper;

    private static final int HOME_NEW_LIMIT = 4;
    private static final int HOME_HOT_LIMIT = 4;
    private static final int FLOOR_GOODS_LIMIT = 6;
    private static final int HEAD_CHILD_GOODS_LIMIT = 6;

    @Override
    public List<HomeBannerVO> getBanner(Integer distributionSite) {
        int site = distributionSite == null ? 1 : distributionSite;
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("distribution_site", site).orderByAsc("id");

        return bannerMapper.selectList(wrapper).stream().map(banner -> {
            HomeBannerVO vo = new HomeBannerVO();
            vo.setId(banner.getId());
            vo.setImgUrl(banner.getImgUrl());
            vo.setHrefUrl(banner.getHrefUrl());
            return vo;
        }).toList();
    }

    @Override
    public List<GoodsSimpleVO> getNewGoods() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("publish_time").last("LIMIT " + HOME_NEW_LIMIT);
        return goodsMapper.selectList(wrapper).stream().map(this::toGoodsSimpleVO).toList();
    }

    @Override
    public List<HomeHotGoodsVO> getHotGoods() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sales_count").last("LIMIT " + HOME_HOT_LIMIT);

        return goodsMapper.selectList(wrapper).stream().map(goods -> {
            HomeHotGoodsVO vo = new HomeHotGoodsVO();
            vo.setId(goods.getId());
            vo.setPicture(goods.getPicture());
            vo.setTitle(goods.getName());
            vo.setAlt(goods.getName()); // 数据库没有 alt 字段，用 name 兜底
            return vo;
        }).toList();
    }

    @Override
    public List<HomeGoodsVO> getHomeGoods() {
        // 1. 获取一级分类 (parentId = 0)
        List<Category> firstCategories = categoryMapper.selectList(
                new QueryWrapper<Category>().eq("parent_id", 0).orderByAsc("sort")
        );

        List<HomeGoodsVO> result = new ArrayList<>();
        for (Category first : firstCategories) {
            // 2. 获取二级分类 ID 列表
            List<Long> secondIds = getSecondLevelIds(first.getId());
            // 3. 查询二级分类下的商品
            List<Goods> goodsList = listGoodsByCategoryIds(secondIds, FLOOR_GOODS_LIMIT);

            HomeGoodsVO vo = new HomeGoodsVO();
            vo.setId(first.getId());
            vo.setName(first.getName());
            vo.setPicture(first.getPicture()); // 一级分类封面
            vo.setSaleInfo(first.getSaleInfo());
            vo.setGoods(goodsList.stream().map(this::toGoodsSimpleVO).toList());
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<HomeCategoryHeadVO> getCategoryHead() {
        // 1. 获取一级分类
        List<Category> firstCategories = categoryMapper.selectList(
                new QueryWrapper<Category>().eq("parent_id", 0).orderByAsc("sort")
        );
        // 2. 获取所有二级分类
        List<Category> allSecondCategories = categoryMapper.selectList(
                new QueryWrapper<Category>().ne("parent_id", 0).orderByAsc("sort")
        );

        // 3. 按 parentId 分组
        Map<Long, List<Category>> secondByParent = allSecondCategories.stream()
                .collect(Collectors.groupingBy(Category::getParentId));

        List<HomeCategoryHeadVO> result = new ArrayList<>();
        for (Category first : firstCategories) {
            List<Category> seconds = secondByParent.getOrDefault(first.getId(), Collections.emptyList());
            List<HomeCategoryChildVO> children = new ArrayList<>();
            List<GoodsSimpleVO> firstGoods = new ArrayList<>();

            for (Category second : seconds) {
                List<Goods> goodsList = listGoodsByCategoryId(second.getId(), HEAD_CHILD_GOODS_LIMIT);

                HomeCategoryChildVO child = new HomeCategoryChildVO();
                child.setId(second.getId());
                child.setName(second.getName());
                child.setPicture(second.getPicture());
                child.setGoods(goodsList.stream().map(this::toGoodsSimpleVO).toList());
                children.add(child);

                // 将二级分类下的商品加入一级分类的推荐位（最多6个）
                for (GoodsSimpleVO item : child.getGoods()) {
                    if (firstGoods.size() >= HEAD_CHILD_GOODS_LIMIT) break;
                    firstGoods.add(item);
                }
            }

            HomeCategoryHeadVO vo = new HomeCategoryHeadVO();
            vo.setId(first.getId());
            vo.setName(first.getName());
            vo.setChildren(children);
            vo.setGoods(firstGoods);
            result.add(vo);
        }
        return result;
    }

    // ================= 私有辅助方法 =================

    private List<Long> getSecondLevelIds(Long parentId) {
        return categoryMapper.selectList(
                new QueryWrapper<Category>().eq("parent_id", parentId)
        ).stream().map(Category::getId).toList();
    }

    private List<Goods> listGoodsByCategoryId(Long categoryId, int limit) {
        return listGoodsByCategoryIds(Collections.singletonList(categoryId), limit);
    }

    private List<Goods> listGoodsByCategoryIds(List<Long> categoryIds, int limit) {
        if (categoryIds == null || categoryIds.isEmpty()) return Collections.emptyList();
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.in("category_id", categoryIds).orderByDesc("id").last("LIMIT " + limit);
        return goodsMapper.selectList(wrapper);
    }

    private GoodsSimpleVO toGoodsSimpleVO(Goods goods) {
        GoodsSimpleVO vo = new GoodsSimpleVO();
        vo.setId(goods.getId());
        vo.setName(goods.getName());
        vo.setPicture(goods.getPicture());
        vo.setPrice(goods.getPrice());
        return vo;
    }
}