package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Deprecated
@Import(LotionLog4j2MakerConfig.class)
public @interface EnableLotionLog4j2 {
}
