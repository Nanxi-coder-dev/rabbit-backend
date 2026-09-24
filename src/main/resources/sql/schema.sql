-- ============================================================
-- Rabbit 电商后端 - 建表脚本
-- 成员 D 负责
-- 数据库: MySQL 5.7+ / 8.0
-- 字符集: utf8mb4
-- ============================================================

CREATE DATABASE IF NOT EXISTS rabbit DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE rabbit;

-- ============================================================
-- 1. 用户表
-- ============================================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `account`     VARCHAR(50)  NOT NULL COMMENT '登录账号',
    `password`    VARCHAR(100) NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname`    VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    `avatar`      VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `phone`       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    `gender`      TINYINT      DEFAULT 0 COMMENT '性别 0未知 1男 2女',
    `status`      TINYINT      DEFAULT 1 COMMENT '状态 0禁用 1正常',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      DEFAULT 0 COMMENT '逻辑删除 0未删 1已删',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ============================================================
-- 2. 分类表（一级+二级，树形结构）
--    字段对齐 A 成员需求: id, name, parent_id, picture, sale_info, sort
-- ============================================================
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name`        VARCHAR(50)  NOT NULL COMMENT '分类名称',
    `parent_id`   BIGINT       DEFAULT 0 COMMENT '父分类ID，0表示一级分类',
    `level`       TINYINT      NOT NULL COMMENT '层级 1一级 2二级',
    `picture`     VARCHAR(255) DEFAULT NULL COMMENT '分类图片URL',
    `sale_info`   VARCHAR(100) DEFAULT NULL COMMENT '营销标语（首页展示用）',
    `sort`        INT          DEFAULT 0 COMMENT '排序号，越小越靠前',
    `status`      TINYINT      DEFAULT 1 COMMENT '状态 0禁用 1启用',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      DEFAULT 0 COMMENT '逻辑删除 0未删 1已删',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ============================================================
-- 3. 商品表
--    基础字段对齐 A: id, name, `desc`, price, main_pictures, category_id
--    扩展字段供 E（商品详情）使用: old_price, specs, details, sales_count 等
-- ============================================================
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
    `id`             BIGINT        NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `name`           VARCHAR(200)  NOT NULL COMMENT '商品名称',
    `desc`           VARCHAR(500)  DEFAULT NULL COMMENT '商品描述（desc为关键字，用反引号）',
    `price`          DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '销售价（元）',
    `old_price`      DECIMAL(10,2) DEFAULT 0.00 COMMENT '原价（元）',
    `main_pictures`  TEXT          DEFAULT NULL COMMENT '主图JSON数组 ["url1","url2"]',
    `category_id`    BIGINT        NOT NULL COMMENT '二级分类ID',
    `brand_name`     VARCHAR(100)  DEFAULT NULL COMMENT '品牌名称',
    `brand_logo`     VARCHAR(255)  DEFAULT NULL COMMENT '品牌Logo URL',
    `specs`          TEXT          DEFAULT NULL COMMENT '商品规格JSON',
    `details`        TEXT          DEFAULT NULL COMMENT '详情图JSON数组',
    `sales_count`    INT           DEFAULT 0 COMMENT '销量（热榜排序用）',
    `comment_count`  INT           DEFAULT 0 COMMENT '评论数',
    `collect_count`  INT           DEFAULT 0 COMMENT '收藏数',
    `evaluate_num`   INT           DEFAULT 0 COMMENT '评价数（分类排序用）',
    `order_num`      INT           DEFAULT 0 COMMENT '订单数（分类排序用）',
    `publish_time`   DATETIME      DEFAULT NULL COMMENT '发布时间（新鲜好物排序用）',
    `is_new`         TINYINT       DEFAULT 0 COMMENT '是否新品 0否 1是',
    `status`         TINYINT       DEFAULT 1 COMMENT '状态 0下架 1上架',
    `create_time`    DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        TINYINT       DEFAULT 0 COMMENT '逻辑删除 0未删 1已删',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_sales_count` (`sales_count`),
    KEY `idx_publish_time` (`publish_time`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ============================================================
-- 4. 商品SKU表
-- ============================================================
DROP TABLE IF EXISTS `goods_sku`;
CREATE TABLE `goods_sku` (
    `id`          BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
    `goods_id`    BIGINT        NOT NULL COMMENT '商品ID',
    `sku_code`    VARCHAR(50)   DEFAULT NULL COMMENT 'SKU编码',
    `price`       DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT 'SKU价格（元）',
    `old_price`   DECIMAL(10,2) DEFAULT 0.00 COMMENT 'SKU原价（元）',
    `inventory`   INT           DEFAULT 0 COMMENT '库存',
    `picture`     VARCHAR(255)  DEFAULT NULL COMMENT 'SKU图片URL',
    `specs`       TEXT          DEFAULT NULL COMMENT 'SKU规格JSON',
    `create_time` DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT       DEFAULT 0 COMMENT '逻辑删除 0未删 1已删',
    PRIMARY KEY (`id`),
    KEY `idx_goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU表';

-- ============================================================
-- 5. 轮播图表
-- ============================================================
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
    `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'Banner ID',
    `img_url`           VARCHAR(255) NOT NULL COMMENT '图片URL',
    `href_url`          VARCHAR(255) DEFAULT NULL COMMENT '跳转链接',
    `distribution_site` TINYINT      DEFAULT 1 COMMENT '投放位置 1首页 2商品页',
    `sort`              INT          DEFAULT 0 COMMENT '排序号',
    `status`            TINYINT      DEFAULT 1 COMMENT '状态 0禁用 1启用',
    `create_time`       DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           TINYINT      DEFAULT 0 COMMENT '逻辑删除 0未删 1已删',
    PRIMARY KEY (`id`),
    KEY `idx_distribution_site` (`distribution_site`),
    KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- ============================================================
-- 6. 购物车表
-- ============================================================
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    `user_id`     BIGINT   NOT NULL COMMENT '用户ID',
    `sku_id`      BIGINT   NOT NULL COMMENT 'SKU ID',
    `count`       INT      DEFAULT 1 COMMENT '商品数量',
    `selected`    TINYINT  DEFAULT 1 COMMENT '是否选中 0未选 1选中',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT  DEFAULT 0 COMMENT '逻辑删除 0未删 1已删',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_sku` (`user_id`, `sku_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ============================================================
SELECT '所有6张表创建完成！' AS result;
SHOW TABLES;