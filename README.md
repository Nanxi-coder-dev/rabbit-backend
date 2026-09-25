# Rabbit 电商后端项目

桂林理工大学 Web 系统开发实习 - 小组项目后端

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 4.1.1 | 核心框架 |
| MyBatis-Plus | 3.5.16 | ORM 框架 |
| MySQL | 8.0 | 数据库 |
| JWT (jjwt) | 0.12.6 | 身份认证 |
| Lombok | - | 简化代码 |
| JDK | 17
| Maven | 3.6+ | 项目构建 |

## 项目结构

```
rabbit-backend/
├── src/main/java/com/rabbit/
│   ├── RabbitApplication.java      # 启动类（D）
│   ├── config/                      # 配置类（D）
│   │   ├── MybatisPlusConfig.java  # 分页插件
│   │   └── CorsConfig.java         # 跨域配置
│   ├── common/                      # 通用类（A）
│   │   ├── Result.java              # 统一返回体
│   │   └── ResultCode.java         # 状态码枚举
│   ├── exception/                   # 异常处理（A）
│   │   ├── BizException.java        # 业务异常
│   │   └── GlobalExceptionHandler.java
│   ├── interceptor/                 # 拦截器（A）
│   │   └── JwtInterceptor.java
│   ├── controller/                  # 控制器（A/B/C/E）
│   ├── service/                     # 服务层
│   │   └── impl/
│   ├── mapper/                      # 数据访问层
│   ├── entity/                      # 实体类
│   └── vo/                          # 视图对象
├── src/main/resources/
│   ├── application.yml               # 配置文件（D）
│   ├── mapper/                       # MyBatis XML
│   └── sql/                          # 按模块拆分，可独立 source
│       ├── 01_user.sql               # 用户表（建表+数据）
│       ├── 02_category.sql           # 分类表（建表+数据）
│       ├── 03_goods.sql              # 商品表+SKU表（建表+数据）
│       ├── 04_banner.sql             # 轮播图表（建表+数据）
│       └── 05_cart.sql               # 购物车表（建表+数据）
└── pom.xml                           # Maven 配置（D）
```

## 快速开始

### 1. 数据库初始化

```bash
# 登录 MySQL
mysql -u root -p

# 执行建表脚本
source src/main/resources/sql/schema.sql

# 执行种子数据
source src/main/resources/sql/seed.sql
```

或者直接在命令行执行：
```bash
mysql -u root -p < src/main/resources/sql/schema.sql
mysql -u root -p < src/main/resources/sql/seed.sql
```

### 2. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rabbit?...
    username: root
    password: 你的密码
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

启动成功后访问：http://localhost:8080

### 4. 测试账号

| 账号 | 密码 | 说明 |
|------|------|------|
| admin | 123456 | 管理员 |
| test | 123456 | 测试用户 |

## 数据库设计（6张表）

| 表名 | 说明 | 关键字段 |
|------|------|----------|
| user | 用户表 | account, password, nickname |
| category | 分类表 | name, parent_id, level（树形：一级+二级） |
| goods | 商品表 | name, category_id, price, specs(JSON), main_pictures(JSON) |
| goods_sku | 商品SKU表 | goods_id, price, inventory, specs(JSON) |
| banner | 轮播图表 | image_url, distribution_site(1首页/2商品页) |
| cart | 购物车表 | user_id, sku_id, count, selected |

### 种子数据统计

- 用户：2 个（admin / test，密码 123456，BCrypt 加密）
- 分类：58 个（9 个一级 + 49 个二级，来自黑马真实接口）
- Banner：5 个（首页 + 商品页，去重）
- 商品：121 个（多来源合并去重，3 个有完整详情含 SKU）
- SKU：3 个（仅 3 个商品有完整 SKU，后续补抓）

## 接口文档

### 统一返回格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 接口列表

#### A 成员 - 用户与购物车

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| POST | /login | 用户登录 | 否 |
| GET | /member/cart | 购物车列表 | 是 |
| POST | /member/cart | 加入购物车 | 是 |
| DELETE | /member/cart | 删除购物车商品 | 是 |
| POST | /member/cart/merge | 合并购物车 | 是 |

**登录请求：**
```json
POST /login
{
  "account": "admin",
  "password": "123456"
}
```

**登录响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "userInfo": {
      "id": 1,
      "account": "admin",
      "nickname": "管理员",
      "avatar": "..."
    }
  }
}
```

#### B 成员 - 首页

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| GET | /home/banner | 轮播图 | 否 |
| GET | /home/new | 新鲜好物 | 否 |
| GET | /home/hot | 人气推荐 | 否 |
| GET | /home/goods | 所有商品模块 | 否 |
| GET | /home/category/head | 首页分类导航 | 否 |

**轮播图参数：**
- `distributionSite`: 1=首页（默认），2=商品页

#### C 成员 - 分类与商品列表

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| GET | /category | 分类数据 | 否 |
| GET | /category/sub/filter | 二级分类筛选 | 否 |
| POST | /category/goods/temporary | 分类商品列表（分页+排序） | 否 |

**分类商品列表请求：**
```json
POST /category/goods/temporary
{
  "categoryId": 101,
  "page": 1,
  "pageSize": 20,
  "sortField": "publishTime"
}
```
- `sortField`: publishTime（最新）/ orderNum（销量）/ evaluateNum（评价）

#### E 成员 - 商品详情

| 方法 | 路径 | 说明 | 鉴权 |
|------|------|------|------|
| GET | /goods | 商品详情 | 否 |
| GET | /goods/hot | 热榜商品 | 否 |
| GET | /goods/relevant | 相关推荐/猜你喜欢 | 否 |

**商品详情参数：**
- `id`: 商品ID

**相关推荐参数：**
- `limit`: 返回数量，默认4

**商品详情响应结构：**
```json
{
  "code": 200,
  "data": {
    "id": 1,
    "name": "智能手机 01",
    "price": 4240,
    "oldPrice": 4740,
    "mainPictures": ["...", "..."],
    "categories": [
      { "id": 101, "name": "智能手机" },
      { "id": 1, "name": "手机数码" }
    ],
    "brand": { "name": "RabbitPhone", "logo": "..." },
    "specs": [
      {
        "name": "颜色",
        "values": [{ "name": "曜石黑", "picture": "", "desc": "" }]
      }
    ],
    "skus": [
      {
        "id": 1,
        "skuCode": "SKU001-01",
        "price": 4240,
        "oldPrice": 4740,
        "inventory": 300,
        "picture": "...",
        "specs": [{ "name": "颜色", "valueName": "曜石黑" }]
      }
    ],
    "details": { "pictures": ["...", "..."] }
  }
}
```

## 成员分工

| 成员 | 负责模块 | 接口 |
|------|----------|------|
| A | 基础架构 + 用户 + 购物车 | Result、JWT、拦截器、/login、/member/cart/** |
| B | 首页 | /home/banner、/home/new、/home/hot、/home/goods、/home/category/head |
| C | 分类 + 商品列表 | /category、/category/sub/filter、/category/goods/temporary |
| D | 数据库 + 骨架 + 配置 + 联调 | 6张表、seed.sql、pom.xml、application.yml、分页配置 |
| E | 商品详情 | /goods、/goods/hot、/goods/relevant |

## 依赖关系

```
D（数据库+骨架）──> A（Result+JWT）──> B/C/E（业务接口）
                        │
                        └──> 购物车（需解析Token）
```

- D 最先开工：建表 SQL + 项目骨架 + 种子数据
- A 第二优先：Result<T>、JwtUtil、拦截器是 B/C/E 的前提
- B、C 并行：互不依赖，都需要 D 的表和 A 的 Result
- E 独立：只依赖 D 的 goods / goods_sku 表

## 注意事项

1. **SKU 对齐**：goods.specs 和 goods_sku.specs 必须严格对齐，否则前端 SKU 选择器无法联动
2. **分类层级**：goods.category_id 指向二级分类，二级分类的 parent_id 指向一级分类
3. **图片 URL**：使用公开可访问的 CDN 地址，不能用本地路径
4. **按模块导入**：SQL 按模块拆分，可按需独立 source，执行顺序 01->05
5. **分页**：使用 MyBatis-Plus 的 Page 对象，已配置分页插件
6. **跨域**：已配置全局跨域，前端开发服务器可直接访问
7. **白名单**：/goods/**、/home/**、/category/**、/login 不需要 Token

## 联调检查清单

- [ ] 数据库能正常连接，6张表创建成功
- [ ] 种子数据导入成功（121商品 + 3SKU + 58分类）
- [ ] 项目启动无报错，端口8080
- [ ] /home/banner 返回至少2条数据
- [ ] /goods?id=1 返回完整详情，specs 和 skus 对齐
- [ ] /goods/hot 按销量降序返回
- [ ] /category/goods/temporary 分页正常
- [ ] /login 返回 token，携带 token 可访问 /member/cart
- [ ] 前端页面能正常渲染所有接口数据