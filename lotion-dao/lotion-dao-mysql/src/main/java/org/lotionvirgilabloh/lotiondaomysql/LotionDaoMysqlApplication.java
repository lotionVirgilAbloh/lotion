package org.lotionvirgilabloh.lotiondaomysql;

import org.lotionvirgilabloh.lotiondaomysql.dao.UserRepository;
import org.lotionvirgilabloh.lotiondaomysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class LotionDaoMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotionDaoMysqlApplication.class, args);
    }

}
