package org.lotionvirgilabloh.lotiondaomysql.dao;

import org.lotionvirgilabloh.lotiondaomysql.entity.DBRealtimeJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtimeJobRepository extends JpaRepository<DBRealtimeJob, Long> {
    DBRealtimeJob getByJobname(String jobname);
}
