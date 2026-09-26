package com.rabbit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品 Mapper
 * 成员 D 负责
 * E（商品详情）可直接继承使用
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}