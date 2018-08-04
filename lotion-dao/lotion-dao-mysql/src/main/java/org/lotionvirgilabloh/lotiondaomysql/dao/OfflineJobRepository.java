package org.lotionvirgilabloh.lotiondaomysql.dao;

import org.lotionvirgilabloh.lotiondaomysql.entity.DBOfflineJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineJobRepository extends JpaRepository<DBOfflineJob, Long> {
    DBOfflineJob getByJobname(String jobname);
}
