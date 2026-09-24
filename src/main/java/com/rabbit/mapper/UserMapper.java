package com.rabbit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper
 * 成员 D 负责（登录模块）
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}