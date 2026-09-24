package com.rabbit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("goods")
public class Goods {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String desc;

    private BigDecimal price;

    private BigDecimal oldPrice;

    /** JSON 列：["url1","url2"] */
    private String mainPictures;

    private Integer salesCount;

    private Integer commentCount;

    private Integer collectCount;

    private String brandName;

    /** JSON 列：[{"name":"材质","value":"铝合金"}] */
    private String detailsProperties;

    /** JSON 列：["url1","url2"] */
    private String detailsPictures;

    /** JSON 列：[{"id":1,"name":"颜色","values":[{"name":"黑色","picture":null}]}] */
    private String specs;

    private Long categoryId;

    private LocalDateTime publishTime;

    private Integer isNew;

    private Integer orderNum;

    private Integer evaluateNum;
}
