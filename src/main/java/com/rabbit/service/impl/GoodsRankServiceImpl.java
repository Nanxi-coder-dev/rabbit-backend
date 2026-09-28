package com.rabbit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.entity.Goods;
import com.rabbit.mapper.GoodsMapper;
import com.rabbit.service.GoodsRankService;
import com.rabbit.vo.GoodsItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 热榜 + 相关推荐实现（成员 C）。
 *
 * 设计稿 4.4 契约：
 *   GET /goods/hot?id&type&limit      -> [{id, name, desc, picture, price}]
 *   GET /goods/relevant?limit         -> [{id, name, desc, picture, price}]
 *
 * 排序口径：设计稿「待确认事项」已注明 /goods/hot 的 type 语义（24h / 周榜）
 * 无法从前端推导，当前版本统一按销量榜（sales_count 倒序）简化，
 * type 参数保留接收但暂不影响结果，待产品确认后再扩展。
 */
@Service
@RequiredArgsConstructor
public class GoodsRankServiceImpl implements GoodsRankService {

    private final GoodsMapper goodsMapper;

    /** limit 上限，防止前端传超大值拖垮数据库 */
    private static final int MAX_LIMIT = 20;

    @Override
    public List<GoodsItemVO> hot(Long id, Integer type, Integer limit) {
        int size = normalizeLimit(limit, 3);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<Goods>()
                // 详情页场景排除当前商品本身
                .ne(id != null, Goods::getId, id)
                .orderByDesc(Goods::getSalesCount)
                .orderByDesc(Goods::getId)
                .last("LIMIT " + size);
        return toItemList(goodsMapper.selectList(wrapper));
    }

    @Override
    public List<GoodsItemVO> relevant(Integer limit) {
        int size = normalizeLimit(limit, 4);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<Goods>()
                .orderByDesc(Goods::getSalesCount)
                .orderByDesc(Goods::getId)
                .last("LIMIT " + size);
        return toItemList(goodsMapper.selectList(wrapper));
    }

    /** limit 兜底：null / 小于 1 用默认值，超过上限截断。Controller 已收 Integer，无注入风险 */
    private int normalizeLimit(Integer limit, int defaultValue) {
        if (limit == null || limit < 1) {
            return defaultValue;
        }
        return Math.min(limit, MAX_LIMIT);
    }

    /** 商品 → 卡片 VO。picture 为空时取 main_pictures 第一张（与 A / C 分页逻辑一致） */
    private List<GoodsItemVO> toItemList(List<Goods> list) {
        return list.stream().map(g -> {
            GoodsItemVO vo = new GoodsItemVO();
            vo.setId(g.getId());
            vo.setName(g.getName());
            vo.setDesc(g.getDesc());
            vo.setPrice(g.getPrice());
            vo.setPicture(resolvePicture(g));
            return vo;
        }).collect(Collectors.toList());
    }

    private String resolvePicture(Goods g) {
        if (g.getPicture() != null && !g.getPicture().isBlank()) {
            return g.getPicture();
        }
        return firstPicture(g.getMainPictures());
    }

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
