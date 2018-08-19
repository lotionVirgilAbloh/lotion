package org.lotionvirgilabloh.lotiondaomongo.repository;

import org.lotionvirgilabloh.lotiondaomongo.entity.MongoFormattedException;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FERepository extends MongoRepository<MongoFormattedException, String> {
}
