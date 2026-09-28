-- ============================================================
-- 小兔鲜电商后端 - 用户模块：用户表
-- 成员 D 负责
-- 本文件包含：建表 + 种子数据，可直接 source 执行
-- 执行顺序：01_user -> 02_category -> 03_goods -> 04_banner -> 05_cart
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS user;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- user 种子数据
INSERT INTO user (id, account, password, nickname, avatar) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', ''),
(2, 'test', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户', '');


SET FOREIGN_KEY_CHECKS = 1;
