package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.springframework.context.annotation.Bean;

public class LotionLog4j2MakerConfig {

    @Bean
    public Maker maker() {
        return new Maker();
    }

    class Maker {
    }
}
