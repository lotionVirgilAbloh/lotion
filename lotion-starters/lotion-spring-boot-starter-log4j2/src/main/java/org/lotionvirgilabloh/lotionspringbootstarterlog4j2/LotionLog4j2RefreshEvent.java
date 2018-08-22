package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class LotionLog4j2RefreshEvent extends ApplicationEvent {

    private ApplicationContext applicationContext;

    public LotionLog4j2RefreshEvent(Object source) {
        super(source);
    }

    public LotionLog4j2RefreshEvent(Object source, ApplicationContext applicationContext) {
        super(source);
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
