package org.lotionvirgilabloh.lotiondaomongo;

import org.lotionvirgilabloh.lotiondaomongo.entity.Customer;
import org.lotionvirgilabloh.lotiondaomongo.entity.MongoFormattedException;
import org.lotionvirgilabloh.lotiondaomongo.repository.CustumerRepository;
import org.lotionvirgilabloh.lotiondaomongo.repository.FERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class LotionDaoMongoApplication implements CommandLineRunner {

    @Autowired
    private CustumerRepository repository;

    @Autowired
    private FERepository ferepository;

    public static void main(String[] args) {
        SpringApplication.run(LotionDaoMongoApplication.class, args);
    }


    @RequestMapping("getAll")
    public List<Customer> getAll(String name){
        List<Customer> customer = repository.findByLastName(name);
        return customer;
    }
    @Override
    public void run(String... args) throws Exception {


        //init for custommer
        repository.deleteAll();
        ferepository.deleteAll();

        // save a couple of customers
        /*
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        ferepository.save(new MongoFormattedException(UUID.randomUUID().toString(),"lotiondaoTest"));
        ferepository.save(new MongoFormattedException(UUID.randomUUID().toString(),"lotiondaoTest2"));
        ferepository.save(new MongoFormattedException(UUID.randomUUID().toString(),"lotiondaoTest3"));*/

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }

        for (MongoFormattedException mongoFormattedException : ferepository.findAll()) {
            System.out.println(mongoFormattedException);
        }
//
//        // fetch an individual customer
//        System.out.println("Customer found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(repository.findByFirstName("Alice"));
//
//        System.out.println("Customers found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (Customer customer : repository.findByLastName("Smith")) {
//            System.out.println(customer);
        }

}
