package com.zeti.drools.service;

import com.zeti.drools.entity.Commodity;

import java.math.BigDecimal;

/**
 * Description: (用一句话描述该文件做什么)
 *
 * @Date 2020/3/30 15:17
 * @Author Zeti
 */
public interface DroolsService {

    Commodity selectDiscount(BigDecimal price);

}
