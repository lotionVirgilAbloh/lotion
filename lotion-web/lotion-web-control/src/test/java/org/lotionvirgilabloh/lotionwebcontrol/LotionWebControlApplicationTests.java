package org.lotionvirgilabloh.lotionwebcontrol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lotionvirgilabloh.lotionwebcontrol.service.SshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LotionWebControlApplicationTests {

    @Autowired
    private SshService sshService;

    @Test
    public void contextLoads() {
        /*String[] tests = new String[3];
        tests[0] = "#!/bash/bin";
        tests[1] = "echo 'Am I here?'";
        tests[2] = "echo 'Yes.'";

        sshService.transferFile("47.106.222.205","root","Qinjibo123", "/tfile/", "test.sh", tests);*/
    }

}
