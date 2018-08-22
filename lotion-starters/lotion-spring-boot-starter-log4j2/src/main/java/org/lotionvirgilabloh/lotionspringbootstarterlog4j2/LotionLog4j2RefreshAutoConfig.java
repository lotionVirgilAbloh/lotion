package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 调整为BootstrapConfiguration
 * AutoConfiguration注入的Context只覆盖main线程而BootstrapConfiguration注入的Context可以达到全线程覆盖
 */
@Configuration
@ConditionalOnClass(LotionLog4j2RefreshEventSender.class)
@ConditionalOnBean({LotionLog4j2MakerConfig.Maker.class,LotionLog4j2ApplicationContextProvider.class})
@AutoConfigureAfter(LotionLog4j2AutoConfig.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class LotionLog4j2RefreshAutoConfig {

    @Bean
    @Autowired
    LotionLog4j2RefreshEventSender lotionLog4j2ContextRefreshedEventSender(LotionLog4j2ApplicationContextProvider lotionLog4j2ApplicationContextProvider) {
        return new LotionLog4j2RefreshEventSender(lotionLog4j2ApplicationContextProvider);
    }
}
