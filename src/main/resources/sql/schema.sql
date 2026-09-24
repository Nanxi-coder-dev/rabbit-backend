-- ============================================================
-- 小兔鲜电商后端 - 建表脚本
-- 6张表：user / category / goods / goods_sku / banner / cart
-- ============================================================

DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS goods_sku;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS banner;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user;

-- 用户表
CREATE TABLE user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    account     VARCHAR(50)  NOT NULL COMMENT '账号',
    password    VARCHAR(100) NOT NULL COMMENT '密码（BCrypt哈希）',
    nickname    VARCHAR(50)  DEFAULT '' COMMENT '昵称',
    avatar      VARCHAR(500) DEFAULT '' COMMENT '头像URL',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_account (account)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 分类表（自关联）
CREATE TABLE category (
    id         BIGINT       NOT NULL COMMENT '分类ID',
    name       VARCHAR(50)  NOT NULL COMMENT '分类名称',
    parent_id  BIGINT       NOT NULL DEFAULT 0 COMMENT '父分类ID，0表示一级',
    picture    VARCHAR(500) DEFAULT '' COMMENT '分类图片',
    sale_info  VARCHAR(100) DEFAULT '' COMMENT '营销信息',
    sort       INT          DEFAULT 0 COMMENT '排序',
    create_time DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE goods (
    id            BIGINT        NOT NULL COMMENT '商品ID',
    name          VARCHAR(200)  NOT NULL COMMENT '商品名称',
    `desc`        VARCHAR(500)  DEFAULT '' COMMENT '商品描述',
    price         DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品价格',
    old_price     DECIMAL(10,2) DEFAULT 0.00 COMMENT '原价',
    picture       VARCHAR(500)  DEFAULT '' COMMENT '商品主图',
    main_pictures TEXT          COMMENT '商品图片列表（JSON数组）',
    specs         TEXT          COMMENT '商品规格（JSON数组）',
    inventory     INT           DEFAULT 0 COMMENT '库存',
    sales_count   INT           DEFAULT 0 COMMENT '销量',
    order_num     INT           DEFAULT 0 COMMENT '排序序号',
    category_id   BIGINT        NOT NULL DEFAULT 0 COMMENT '分类ID（二级分类）',
    create_time   DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_category_id (category_id),
    KEY idx_order_num (order_num)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 商品SKU表
CREATE TABLE goods_sku (
    id          BIGINT        NOT NULL COMMENT 'SKU ID',
    goods_id    BIGINT        NOT NULL COMMENT '商品ID',
    price       DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT 'SKU价格',
    old_price   DECIMAL(10,2) DEFAULT 0.00 COMMENT 'SKU原价',
    inventory   INT           DEFAULT 0 COMMENT 'SKU库存',
    picture     VARCHAR(500)  DEFAULT '' COMMENT 'SKU图片',
    specs       TEXT          COMMENT 'SKU规格（JSON数组，与goods.specs对齐）',
    create_time DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_goods_id (goods_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU表';

-- 轮播图表
CREATE TABLE banner (
    id          BIGINT       NOT NULL COMMENT 'Banner ID',
    img_url     VARCHAR(500) NOT NULL COMMENT '图片URL',
    href_url    VARCHAR(500) DEFAULT '' COMMENT '跳转链接',
    title       VARCHAR(100) DEFAULT '' COMMENT '标题',
    type        INT          DEFAULT 1 COMMENT '类型：1-首页',
    sort        INT          DEFAULT 0 COMMENT '排序',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 购物车表
CREATE TABLE cart (
    id          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    user_id     BIGINT   NOT NULL COMMENT '用户ID',
    sku_id      BIGINT   NOT NULL COMMENT 'SKU ID',
    count       INT      NOT NULL DEFAULT 1 COMMENT '数量',
    selected    TINYINT  NOT NULL DEFAULT 1 COMMENT '是否选中：0-否，1-是',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_sku (user_id, sku_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';
