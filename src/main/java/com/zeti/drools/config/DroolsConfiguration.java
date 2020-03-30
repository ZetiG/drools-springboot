package com.zeti.drools.config;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Description: Drools配置
 *
 * @Date 2020/3/30 14:39
 * @Author Zeti
 */
@Slf4j
@Configuration
public class DroolsConfiguration {

    private static final String RULE_PATH = "droolsRules/";

    private final KieServices kieServices = KieServices.Factory.get();

    /**
     * KieFileSystem bean，完整的文件系统
     *
     * @return
     * @throws IOException
     */
    @Bean
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources(RULE_PATH);
        for (Resource resource : resources) {
            String ruleFile;
            ruleFile = RULE_PATH + resource.getFilename();
            log.info("rule-file:{}", resource);
            kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFile,
                    Charset.defaultCharset().name()));
        }
        return kieFileSystem;
    }

    /**
     * KieContainer bean, 一个KieBase的容器，
     * 可以根据kmodule.xml 里描述的KieBase信息来获取具体的KieSession
     *
     * @return
     * @throws IOException
     */
    @Bean
    public KieContainer kieContain() throws IOException {
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    /**
     * KieBase bean，知识库 包含流程、规则、方法等
     *
     * @return
     * @throws IOException
     */
    @Bean
    public KieBase kieBase() throws IOException {
        return kieContain().getKieBase();
    }

    /**
     * KieSession，一个跟Drools引擎打交道的会话，其基于KieBase创建，
     * 它会包含运行时数据，包含“事实 Fact”，并对运行时数据事实进行规则运算
     *
     * @return
     * @throws IOException
     */
    @Bean
    public KieSession kieSession() throws IOException {
        return kieContain().newKieSession();
    }

}
