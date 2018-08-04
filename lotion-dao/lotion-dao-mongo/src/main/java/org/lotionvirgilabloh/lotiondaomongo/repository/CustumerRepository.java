package org.lotionvirgilabloh.lotiondaomongo.repository;

import org.lotionvirgilabloh.lotiondaomongo.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustumerRepository  extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}