package com.zeti.drools.service;

import com.zeti.drools.entity.Commodity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Description: (用一句话描述该文件做什么)
 *
 * @Date 2020/3/30 15:21
 * @Author Zeti
 */
@Service
public class DroolsServiceImpl implements DroolsService {

    @Override
    public Commodity selectDiscount(BigDecimal price) {
        Commodity commodity = new Commodity();
        commodity.setName("商品A");
        commodity.setPrice(price);
        return commodity;
    }
}
