package com.zeti.drools.controller;


import com.zeti.drools.entity.Commodity;
import com.zeti.drools.service.DroolsService;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Description: Drools控制层
 *
 * @Date 2020/3/30 15:17
 * @Author Zeti
 */
@Slf4j
@RestController
@RequestMapping("/drools")
public class DroolsController {

    private static Logger logger = LoggerFactory.getLogger(DroolsController.class);

    @Resource
    private DroolsService droolsService;

    @Resource
    private KieSession kieSession;

    @GetMapping("/{money}")
    public Object droolsRule(@PathVariable String money) {
        Commodity commodity = droolsService.selectDiscount(new BigDecimal(money));
        kieSession.setGlobal("logger", logger);
        kieSession.insert(commodity);
        kieSession.fireAllRules();
        return commodity;
    }

    @GetMapping("/")
    public void droolsRule2() {
        kieSession.insert("Tom");
        kieSession.fireAllRules();
    }


}
