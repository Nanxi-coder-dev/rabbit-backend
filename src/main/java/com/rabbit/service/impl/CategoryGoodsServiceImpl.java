package com.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.common.BizException;
import com.rabbit.dto.GoodsPageQueryDTO;
import com.rabbit.entity.Category;
import com.rabbit.entity.Goods;
import com.rabbit.mapper.CategoryMapper;
import com.rabbit.mapper.GoodsMapper;
import com.rabbit.service.CategoryGoodsService;
import com.rabbit.vo.GoodsItemVO;
import com.rabbit.vo.GoodsPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类商品分页实现（成员 C）。
 *
 * 依赖：
 *  - D 的 goods / category 表（对齐 feature/db-infra 整改后 19 列 goods）
 *  - A 的分页插件（MybatisPlusConfig）与 Result 返回体
 *
 * 兼容说明（重要）：
 *  D 的种子数据里 goods.category_id 并不全是二级分类，
 *  存在直接挂一级分类（如 1005000、109243029）甚至 0 的记录。
 *  因此本实现做两级处理：
 *    传入二级分类 → category_id 精确匹配；
 *    传入一级分类 → category_id ∈ {自身 + 其全部二级分类}。
 *  category_id = 0 的脏数据天然被过滤，如需展示请 D 补数据。
 */
@Service
@RequiredArgsConstructor
public class CategoryGoodsServiceImpl implements CategoryGoodsService {

    private final GoodsMapper goodsMapper;
    private final CategoryMapper categoryMapper;

    /** 每页上限，防止前端传超大 pageSize 拖垮数据库 */
    private static final int MAX_PAGE_SIZE = 100;

    /** 设计稿 4.3 允许的三个排序字段，白名单之外的值一律兜底 */
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("publishTime", "orderNum", "evaluateNum");

    @Override
    public GoodsPageVO page(GoodsPageQueryDTO query) {
        if (query.getCategoryId() == null) {
            throw new BizException("categoryId 不能为空");
        }

        // 1. 参数兜底：页码 / 每页条数 / 排序字段全部做防御，不依赖前端传值正确
        int page = (query.getPage() == null || query.getPage() < 1) ? 1 : query.getPage();
        int pageSize = (query.getPageSize() == null || query.getPageSize() < 1)
                ? 10 : Math.min(query.getPageSize(), MAX_PAGE_SIZE);
        String sortField = normalizeSortField(query.getSortField());

        // 2. 解析分类范围（一级展开二级）
        List<Long> categoryIds = resolveCategoryIds(query.getCategoryId());

        // 3. 分页查询。主排序取白名单字段，id 倒序做稳定次序（publish_time 有 NULL）
        Page<Goods> mpPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<Goods>()
                .in(Goods::getCategoryId, categoryIds)
                .orderByDesc(sortColumn(sortField))
                .orderByDesc(Goods::getId);
        Page<Goods> result = goodsMapper.selectPage(mpPage, wrapper);

        // 4. 组装 VO：字段名严格对齐设计稿 {items, counts, page, pageSize}
        GoodsPageVO vo = new GoodsPageVO();
        vo.setItems(result.getRecords().stream()
                .map(this::toItem)
                .collect(Collectors.toList()));
        vo.setCounts(result.getTotal());
        vo.setPage(page);
        vo.setPageSize(pageSize);
        return vo;
    }

    /**
     * 解析查询范围。
     * 二级分类（parentId != 0）：精确匹配；
     * 一级分类（parentId = 0）：自身 + 全部二级分类（一次索引查询，数据量 58 条无压力）。
     */
    private List<Long> resolveCategoryIds(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BizException("分类不存在");
        }
        if (category.getParentId() != null && category.getParentId() != 0) {
            return List.of(categoryId);
        }
        List<Long> ids = new ArrayList<>();
        ids.add(category.getId());
        List<Category> subs = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>().eq(Category::getParentId, category.getId()));
        subs.forEach(s -> ids.add(s.getId()));
        return ids;
    }

    /** 排序字段白名单校验，非法 / 缺省统一兜底为 orderNum（综合排序） */
    private String normalizeSortField(String sortField) {
        if (sortField == null || sortField.isBlank()
                || !ALLOWED_SORT_FIELDS.contains(sortField)) {
            return "orderNum";
        }
        return sortField;
    }

    /** 白名单字段映射为实体列，避免字符串拼 SQL */
    private SFunction<Goods, ?> sortColumn(String sortField) {
        return switch (sortField) {
            case "publishTime" -> Goods::getPublishTime;
            case "evaluateNum" -> Goods::getEvaluateNum;
            default -> Goods::getOrderNum;
        };
    }

    /** 商品 → 列表卡片。picture 为空时从 main_pictures JSON 数组取第一张（与 A 的兜底逻辑一致） */
    private GoodsItemVO toItem(Goods g) {
        GoodsItemVO vo = new GoodsItemVO();
        vo.setId(g.getId());
        vo.setName(g.getName());
        vo.setDesc(g.getDesc());
        vo.setPrice(g.getPrice());
        vo.setPicture(resolvePicture(g));
        return vo;
    }

    private String resolvePicture(Goods g) {
        if (g.getPicture() != null && !g.getPicture().isBlank()) {
            return g.getPicture();
        }
        return firstPicture(g.getMainPictures());
    }

    /** main_pictures 是 TEXT 存的 JSON 数组字符串，这里轻量取第一张，不引 Jackson */
    private String firstPicture(String mainPictures) {
        if (mainPictures == null || mainPictures.isBlank()) {
            return "";
        }
        String s = mainPictures.trim();
        if (s.startsWith("[")) s = s.substring(1);
        if (s.endsWith("]")) s = s.substring(0, s.length() - 1);
        String[] parts = s.split(",");
        if (parts.length == 0) {
            return "";
        }
        String first = parts[0].trim();
        if (first.startsWith("\"")) first = first.substring(1);
        if (first.endsWith("\"")) first = first.substring(0, first.length() - 1);
        return first;
    }
}
