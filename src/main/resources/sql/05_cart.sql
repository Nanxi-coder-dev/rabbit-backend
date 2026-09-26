-- ============================================================
-- 小兔鲜电商后端 - 购物车模块：购物车表（依赖 user + goods_sku）
-- 成员 D 负责
-- 本文件包含：建表 + 种子数据，可直接 source 执行
-- 执行顺序：01_user -> 02_category -> 03_goods -> 04_banner -> 05_cart
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS cart;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- cart （无种子数据）


SET FOREIGN_KEY_CHECKS = 1;
