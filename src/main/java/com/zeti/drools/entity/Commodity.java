package com.zeti.drools.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description: 商品类-测试类(用一句话描述该文件做什么)
 *
 * @Date 2020/3/30 16:32
 * @Author Zeti
 */
@Data
public class Commodity {

    /**
     * 商品
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 优惠
     */
    private BigDecimal discount;

}
