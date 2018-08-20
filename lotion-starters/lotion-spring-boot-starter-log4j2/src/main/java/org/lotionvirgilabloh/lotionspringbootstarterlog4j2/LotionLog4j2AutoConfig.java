package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnClass(LotionLog4j2PreparedListener.class)
@ConditionalOnBean(LotionLog4j2MakerConfig.Maker.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class LotionLog4j2AutoConfig {

    public LotionLog4j2AutoConfig() {
    }

    @Bean
    LotionLog4j2PreparedListener log4j2PreparedListener() {
        return new LotionLog4j2PreparedListener();
    }
}
