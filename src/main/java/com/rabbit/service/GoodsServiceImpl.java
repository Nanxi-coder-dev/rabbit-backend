package com.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.common.BizException;
import com.rabbit.entity.Category;
import com.rabbit.entity.Goods;
import com.rabbit.entity.GoodsSku;
import com.rabbit.mapper.CategoryMapper;
import com.rabbit.mapper.GoodsMapper;
import com.rabbit.mapper.GoodsSkuMapper;
import com.rabbit.service.GoodsService;
import com.rabbit.vo.GoodsDetailVO;
import com.rabbit.vo.GoodsSimpleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;
    private final GoodsSkuMapper goodsSkuMapper;
    private final CategoryMapper categoryMapper;
    private final ObjectMapper objectMapper;

    @Override
    public GoodsDetailVO getDetail(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BizException("商品不存在");
        }

        GoodsDetailVO vo = new GoodsDetailVO();
        vo.setId(goods.getId());
        vo.setName(goods.getName());
        vo.setDesc(goods.getDesc());
        vo.setPrice(goods.getPrice());
        vo.setOldPrice(goods.getOldPrice());
        vo.setSalesCount(goods.getSalesCount());
        vo.setCommentCount(goods.getCommentCount());
        vo.setCollectCount(goods.getCollectCount());

        // mainPictures
        vo.setMainPictures(parseJson(goods.getMainPictures(), new TypeReference<List<String>>() {}));

        // categories：[0]=二级 [1]=一级，顺序不能反
        vo.setCategories(buildCategories(goods.getCategoryId()));

        // brand
        GoodsDetailVO.BrandVO brand = new GoodsDetailVO.BrandVO();
        brand.setName(goods.getBrandName());
        vo.setBrand(brand);

        // details
        vo.setDetails(buildDetails(goods));

        // specs
        vo.setSpecs(parseJson(goods.getSpecs(),
                new TypeReference<List<GoodsDetailVO.SpecVO>>() {}));

        // skus
        vo.setSkus(buildSkus(goods.getId()));

        return vo;
    }

    /**
     * categories 数组：[0]=二级分类 [1]=一级分类
     */
    private List<GoodsDetailVO.CategoryVO> buildCategories(Long categoryId) {
        if (categoryId == null) {
            return Collections.emptyList();
        }
        Category second = categoryMapper.selectById(categoryId);
        if (second == null) {
            return Collections.emptyList();
        }

        List<GoodsDetailVO.CategoryVO> list = new ArrayList<>();

        // [0] 二级
        GoodsDetailVO.CategoryVO c2 = new GoodsDetailVO.CategoryVO();
        c2.setId(second.getId());
        c2.setName(second.getName());
        list.add(c2);

        // [1] 一级
        if (second.getParentId() != null && second.getParentId() != 0) {
            Category first = categoryMapper.selectById(second.getParentId());
            if (first != null) {
                GoodsDetailVO.CategoryVO c1 = new GoodsDetailVO.CategoryVO();
                c1.setId(first.getId());
                c1.setName(first.getName());
                list.add(c1);
            }
        }
        return list;
    }

    private GoodsDetailVO.DetailsVO buildDetails(Goods goods) {
        GoodsDetailVO.DetailsVO details = new GoodsDetailVO.DetailsVO();
        details.setProperties(parseJson(goods.getDetailsProperties(),
                new TypeReference<List<GoodsDetailVO.PropertyVO>>() {}));
        details.setPictures(parseJson(goods.getDetailsPictures(),
                new TypeReference<List<String>>() {}));
        return details;
    }

    private List<GoodsDetailVO.SkuVO> buildSkus(Long goodsId) {
        List<GoodsSku> skuList = goodsSkuMapper.selectList(
                new LambdaQueryWrapper<GoodsSku>().eq(GoodsSku::getGoodsId, goodsId)
        );

        return skuList.stream().map(sku -> {
            GoodsDetailVO.SkuVO vo = new GoodsDetailVO.SkuVO();
            vo.setId(sku.getId());
            vo.setPrice(sku.getPrice());
            vo.setOldPrice(sku.getOldPrice());
            vo.setInventory(sku.getInventory());
            vo.setSpecs(parseJson(sku.getSpecs(),
                    new TypeReference<List<GoodsDetailVO.SkuSpecVO>>() {}));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<GoodsSimpleVO> getHotGoods(Long id, Integer type, Integer limit) {
        // 简化：按销量取 top-N，排除当前商品
        List<Goods> list = goodsMapper.selectList(
                new LambdaQueryWrapper<Goods>()
                        .ne(id != null, Goods::getId, id)
                        .orderByDesc(Goods::getSalesCount)
                        .last("LIMIT " + (limit == null ? 3 : limit))
        );
        return toSimpleVOList(list);
    }

    @Override
    public List<GoodsSimpleVO> getRelevant(Integer limit) {
        List<Goods> list = goodsMapper.selectList(
                new LambdaQueryWrapper<Goods>()
                        .orderByDesc(Goods::getSalesCount)
                        .last("LIMIT " + (limit == null ? 4 : limit))
        );
        return toSimpleVOList(list);
    }

    private List<GoodsSimpleVO> toSimpleVOList(List<Goods> list) {
        return list.stream().map(g -> {
            GoodsSimpleVO vo = new GoodsSimpleVO();
            vo.setId(g.getId());
            vo.setName(g.getName());
            vo.setDesc(g.getDesc());
            vo.setPrice(g.getPrice());
            // picture 取 mainPictures 的第一个
            List<String> pics = parseJson(g.getMainPictures(), new TypeReference<List<String>>() {});
            vo.setPicture(pics.isEmpty() ? null : pics.get(0));
            return vo;
        }).collect(Collectors.toList());
    }

    private <T> T parseJson(String json, TypeReference<T> typeRef) {
        if (json == null || json.isBlank()) {
            try {
                // 返回空集合
                return objectMapper.readValue("[]", typeRef);
            } catch (Exception e) {
                return null;
            }
        }
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new BizException("JSON 解析失败: " + e.getMessage());
        }
    }
}