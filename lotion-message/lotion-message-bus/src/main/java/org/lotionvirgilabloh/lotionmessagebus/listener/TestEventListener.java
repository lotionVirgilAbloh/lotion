package org.lotionvirgilabloh.lotionmessagebus.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {
//public class TestEventListener implements ApplicationListener<LotionTestEvent> {
    private static final Logger logger= LoggerFactory.getLogger(TestEventListener.class);

//    @Override
//    public void onApplicationEvent(@NonNull LotionTestEvent event) {
//        logger.info(event.getMessage());
//    }

}
