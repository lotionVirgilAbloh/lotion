package org.lotionvirgilabloh.lotionspringbootstarterlog4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class LotionLog4j2RefreshEventSender {

    private LotionLog4j2ApplicationContextProvider log4j2ApplicationContextProvider;

    @Autowired
    public LotionLog4j2RefreshEventSender(LotionLog4j2ApplicationContextProvider lotionLog4j2ApplicationContextProvider) {
        this.log4j2ApplicationContextProvider = lotionLog4j2ApplicationContextProvider;
        ApplicationContext applicationContext = this.log4j2ApplicationContextProvider.getApplicationContext();
        applicationContext.publishEvent(new LotionLog4j2RefreshEvent(this, applicationContext));
    }
}
