package org.lotionvirgilabloh.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

public class LotionWebUserApplicationTests {

    @Test
    public void contextLoads() {
        BCryptPasswordEncoder bce = new BCryptPasswordEncoder(6);
         String  b =  bce.encode("qwer123");
         System.out.print(b);
    }

}
