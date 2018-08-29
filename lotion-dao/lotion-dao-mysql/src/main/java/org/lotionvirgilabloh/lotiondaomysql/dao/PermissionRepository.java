package org.lotionvirgilabloh.lotiondaomysql.dao;

import org.lotionvirgilabloh.lotiondaomysql.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository  extends JpaRepository<Permission,Long> {
}
