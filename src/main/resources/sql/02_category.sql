-- ============================================================
-- 小兔鲜电商后端 - 分类模块：商品分类表（一级+二级，自关联）
-- 成员 D 负责
-- 本文件包含：建表 + 种子数据，可直接 source 执行
-- 执行顺序：01_user -> 02_category -> 03_goods -> 04_banner -> 05_cart
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS category;

CREATE TABLE category (
    id          BIGINT       NOT NULL COMMENT '分类ID',
    name        VARCHAR(50)  NOT NULL COMMENT '分类名称',
    parent_id   BIGINT       NOT NULL DEFAULT 0 COMMENT '父分类ID，0表示一级',
    picture     VARCHAR(500) DEFAULT '' COMMENT '分类图片',
    sale_info   VARCHAR(100) DEFAULT '' COMMENT '营销信息',
    sort        INT          DEFAULT 0 COMMENT '排序',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- category 种子数据
INSERT INTO category (id, name, parent_id, picture, sale_info, sort) VALUES
(1005000, '居家', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/201516e3-25d0-48f5-bcee-7f0cafb14176.png', '', 0),
(1005999003, '居家生活用品', 1005000, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-04-22/7f6a7b20-7902-4b43-b9c5-f33151ef1334.jpg?quality=95&imageView', '', 1),
(1008017, '收纳', 1005000, 'https://yanxuan.nosdn.127.net/366989e4d730594e86fcd60b5ab19acc.png?quality=95&imageView', '', 2),
(1017000, '宠物食品', 1005000, 'https://yanxuan.nosdn.127.net/b42a85ef15f856081ea9f49e5f6893e2.png?quality=95&imageView', '', 3),
(109243003, '艺术藏品', 1005000, 'https://yanxuan.nosdn.127.net/9544b81aaa14c26a8038c2365ff3c2bc.png?quality=95&imageView', '', 4),
(109248004, '宠物用品', 1005000, 'https://yanxuan.nosdn.127.net/e766b09029ca00680d1e651b5cdc42bd.png?quality=95&imageView', '', 5),
(109293000, '家庭医疗', 1005000, 'https://yanxuan.nosdn.127.net/3f34039fa8c26e18e2f4fc96ed8cb6de.png?quality=95&imageView', '', 6),
(109308000, '中医保健', 1005000, 'https://yanxuan.nosdn.127.net/2bfba997fd031317caecc4f0bad17569.png?quality=95&imageView', '', 7),
(1005002, '美食', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/cf82e5b4-bf1b-4c68-aa86-96f66e8e5282.png', '', 8),
(1005012, '南北干货', 1005002, 'https://yanxuan.nosdn.127.net/9af51a1090fd32f668b14451f06d6e72.png?quality=95&imageView', '', 9),
(1036003, '调味酱菜', 1005002, 'https://yanxuan.nosdn.127.net/5fae33a840870b487cc903535383bf97.png?quality=95&imageView', '', 10),
(109201001, '方便食品', 1005002, 'https://yanxuan.nosdn.127.net/f9872b4aad6c0a943d45629ac96ee8d3.png?quality=95&imageView', '', 11),
(109206007, '米面粮油', 1005002, 'https://yanxuan.nosdn.127.net/8578759aed2268f7aa8641273cac7cb3.png?quality=95&imageView', '', 12),
(109264007, '名酒馆', 1005002, 'https://yanxuan.nosdn.127.net/91413b1476a0697bb0592609a42d4498.png?quality=95&imageView', '', 13),
(109285003, '进口酒', 1005002, 'https://yanxuan.nosdn.127.net/bf705060f01b60fe9c11c345931b1891.png?quality=95&imageView', '', 14),
(1010000, '服饰', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/33e1f5de-0fdb-4cfa-9ba9-781233024b53.png', '', 15),
(109303000, '钱包/胸包', 1010000, 'https://yanxuan.nosdn.127.net/237613bc9c22eb422dade63e3ed7c61a.png?quality=95&imageView', '', 16),
(109311005, '女式靴子', 1010000, 'https://yanxuan.nosdn.127.net/62c5dacf3e0cbe8e4188ccd263358d1a.png?quality=95&imageView', '', 17),
(109311006, '女式休闲鞋', 1010000, 'https://yanxuan.nosdn.127.net/8cd3a76ffffb14e9fe92ad2369117af0.png?quality=95&imageView', '', 18),
(109311007, '女式运动鞋', 1010000, 'https://yanxuan.nosdn.127.net/7be561f2ddc2179a7e116c413636eba9.png?quality=95&imageView', '', 19),
(109315000, '11.11购物狂欢', 1010000, 'https://yanxuan.nosdn.127.net/b29297263032957553d7153b309db74b.png?quality=95&imageView', '', 20),
(109318003, '【年末狂欢季】', 1010000, 'https://yanxuan.nosdn.127.net/8f8092d5bf6a133a8cb59ab7b9f790e9.png?quality=95&imageView', '', 21),
(1011000, '母婴', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/b514a526-4010-4ce8-8cb9-757ed382f84a.png', '', 22),
(1020003, 'T恤/polo/衬衫', 1011000, 'https://yanxuan.nosdn.127.net/1f0089afcec911db7202fbcdae57d5f8.png?quality=95&imageView', '', 23),
(1037006, '儿童鞋', 1011000, 'https://yanxuan.nosdn.127.net/7fd14a409302391da16970981cacd336.png?quality=95&imageView', '', 24),
(109243018, '外套/套装', 1011000, 'https://yanxuan.nosdn.127.net/773a8777f66c286f97af6d74a27d7fe1.png?quality=95&imageView', '', 25),
(109243019, '裤子/裙装', 1011000, 'https://yanxuan.nosdn.127.net/a8c52cea5f953019484a74883ad8f14b.png?quality=95&imageView', '', 26),
(109243021, '连体衣/礼盒', 1011000, 'https://yanxuan.nosdn.127.net/773677cc0922628152a9b3cbd862426f.png?quality=95&imageView', '', 27),
(109243022, '学步鞋', 1011000, 'https://yanxuan.nosdn.127.net/9a50280bb69e1c12f557f601cce1c480.png?quality=95&imageView', '', 28),
(1013001, '个护', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/d38a73b8-cd03-48aa-a60b-e7c4e16667ed.png', '', 29),
(1009000, '家庭清洁', 1013001, 'https://yanxuan.nosdn.127.net/718318c0d3b55d011fcb7c7c843902ce.png?quality=95&imageView', '', 30),
(1020002, '浴室用品', 1013001, 'https://yanxuan.nosdn.127.net/dfb6142de1bd2f59b251eb8f7c7ea2fb.png?quality=95&imageView', '', 31),
(109243016, '餐厨清洁', 1013001, 'https://yanxuan.nosdn.127.net/55d927e337c1f6d394359e99ef72a621.png?quality=95&imageView', '', 32),
(109256012, '纸品', 1013001, 'https://yanxuan.nosdn.127.net/07fef43b0d14882d6662233ab30dc588.png?quality=95&imageView', '', 33),
(109256013, '干湿巾', 1013001, 'https://yanxuan.nosdn.127.net/985897ea31fdfc159e12696f4dbb4c13.png?quality=95&imageView', '', 34),
(109261055, '毛巾浴巾', 1013001, 'https://yanxuan.nosdn.127.net/949c5f8b077cf386ff9f1f18bec3408b.png?quality=95&imageView', '', 35),
(1019000, '严选', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/4b02f01f-a365-4b6c-9f7a-8b0f591dda02.png', '', 36),
(1065004, '滋补保健', 1019000, 'https://yanxuan.nosdn.127.net/1e619a2b22f40bf83070e6f8f6e0c8ff.png?quality=95&imageView', '', 37),
(109256014, '床品家纺', 1019000, 'https://yanxuan.nosdn.127.net/e6580910c1f98ed61bda867aeaf07929.png?quality=95&imageView', '', 38),
(109256015, '锅具配件', 1019000, 'https://yanxuan.nosdn.127.net/50ccbf04857e86cccf44d25da0577deb.png?quality=95&imageView', '', 39),
(109275000, '清洁用品', 1019000, 'https://yanxuan.nosdn.127.net/926d919bc3e95f5c93dc5dc973faa378.png?quality=95&imageView', '', 40),
(109309012, '个护电器', 1019000, 'https://yanxuan.nosdn.127.net/7a0eea3c515ad247c092749bcdd29855.png?quality=95&imageView', '', 41),
(1043000, '数码', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/9660870d-6a59-4624-8064-b3a8cbf50d5c.png', '', 42),
(1008006, '影音娱乐', 1043000, 'https://yanxuan.nosdn.127.net/f5797ca77cfe413e7753ec69f9bd4bb1.png?quality=95&imageView', '', 43),
(1022000, '3C数码', 1043000, 'https://yanxuan.nosdn.127.net/99b8f97b2e5449606fd558574aa15982.png?quality=95&imageView', '', 44),
(1028001, '乐器', 1043000, 'https://yanxuan.nosdn.127.net/da0ac345e98c04594b697b56ebc373a5.png?quality=95&imageView', '', 45),
(109243035, '手机配件', 1043000, 'https://yanxuan.nosdn.127.net/0276d68f4b7a03bbd16675ada6e707ff.png?quality=95&imageView', '', 46),
(109243036, '车载用品', 1043000, 'https://yanxuan.nosdn.127.net/3f45fbcdac7e8532b6a1570e6d7fe171.png?quality=95&imageView', '', 47),
(109243046, '办公文具', 1043000, 'https://yanxuan.nosdn.127.net/801583d2f58274b13dc6a03daed1c3c9.png?quality=95&imageView', '', 48),
(109243029, '运动', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/7d19752c-baff-49b6-bd02-5ece1d729214.png', '', 49),
(109312000, '健身大器械', 109243029, 'https://yanxuan.nosdn.127.net/6a1d37ffb2e28622a71e3c4415eaee35.png?quality=95&imageView', '', 50),
(109312001, '健身小器械', 109243029, 'https://yanxuan.nosdn.127.net/8c9f060e6fddb2b75af851a9a2c60087.png?quality=95&imageView', '', 51),
(109312002, '城市出行', 109243029, 'https://yanxuan.nosdn.127.net/b41b50710c3823f44a9f5b549a67ca81.png?quality=95&imageView', '', 52),
(109312003, '运动护具', 109243029, 'https://yanxuan.nosdn.127.net/ef9bcb99d88b3a1cfd9d2e120c158c21.png?quality=95&imageView', '', 53),
(109313000, '垂钓', 109243029, 'https://yanxuan.nosdn.127.net/c4eeeae307d0562cf3e95303146282b2.png?quality=95&imageView', '', 54),
(109313003, '户外装备', 109243029, 'https://yanxuan.nosdn.127.net/6bbd92c68741c857d842f0afd1c7bdd5.png?quality=95&imageView', '', 55),
(19999999, '杂项', 0, 'http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-06/4ff20b9e-8150-4bd3-87a3-0cd6766938dd.png', '', 56),
(19999999001, '家庭清洁杂项', 19999999, 'https://yanxuan.nosdn.127.net/718318c0d3b55d011fcb7c7c843902ce.png?quality=95&imageView', '', 57);


SET FOREIGN_KEY_CHECKS = 1;
