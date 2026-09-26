package com.rabbit.dto;

import lombok.Data;

/**
 * 商品分页查询入参（POST /category/goods/temporary 请求体）。
 * 契约来自设计稿 4.3：
 *   body {categoryId, page, pageSize, sortField}
 *   sortField 取值：publishTime（最新上架）/ orderNum（最多购买）/ evaluateNum（最多评价）
 */
@Data
public class GoodsPageQueryDTO {

    /** 分类ID：可传一级或二级分类（一级会展开其全部二级分类，见 Service 说明） */
    private Long categoryId;

    /** 页码，默认 1（小于 1 时按 1 处理） */
    private Integer page;

    /** 每页条数，默认 10（小于 1 按 10，大于 100 按 100 截断，防刷） */
    private Integer pageSize;

    /** 排序字段：publishTime / orderNum / evaluateNum，非法值兜底为 orderNum */
    private String sortField;
}
