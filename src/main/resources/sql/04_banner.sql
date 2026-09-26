-- ============================================================
-- 小兔鲜电商后端 - 轮播图模块：首页/商品页 Banner
-- 成员 D 负责
-- 本文件包含：建表 + 种子数据，可直接 source 执行
-- 执行顺序：01_user -> 02_category -> 03_goods -> 04_banner -> 05_cart
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS banner;

CREATE TABLE banner (
    id                BIGINT       NOT NULL COMMENT 'Banner ID',
    img_url           VARCHAR(500) NOT NULL COMMENT '图片URL',
    href_url          VARCHAR(500) DEFAULT '' COMMENT '跳转链接',
    distribution_site INT          DEFAULT 1 COMMENT '投放位置：1-首页，2-商品页',
    create_time       DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- banner 种子数据
INSERT INTO banner (id, img_url, href_url, distribution_site) VALUES
(16, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/dfc11bb0-4af5-4e9b-9458-99f615cc685a.jpg', '/category/1005000', 1),
(19, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/6d202d8e-bb47-4f92-9523-f32ab65754f4.jpg', '/category/1013001', 1),
(20, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/e83efb1b-309c-46f7-98a3-f1fefa694338.jpg', '/category/1005000', 1),
(18, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/1ba86bcc-ae71-42a3-bc3e-37b662f7f07e.jpg', '/category/1013001', 1),
(17, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-15/4a79180a-1a5a-4042-8a77-4db0b9c800a8.jpg', '/category/1019000', 1);


SET FOREIGN_KEY_CHECKS = 1;
