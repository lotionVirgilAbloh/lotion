package org.lotionvirgilabloh.lotiondaomysql.dao;

import org.lotionvirgilabloh.lotiondaomysql.entity.DBJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<DBJob,Long> {


    DBJob getByJobnameIs(String jobName);

}