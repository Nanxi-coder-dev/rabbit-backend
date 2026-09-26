package com.rabbit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类 Mapper
 * 成员 D 负责
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}