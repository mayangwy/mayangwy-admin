package org.mayangwy.admin.modules.system.dao;

import org.mayangwy.admin.modules.system.entity.SysOperateLogPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOperateLogRepository extends JpaRepository<SysOperateLogPO, Long>, JpaSpecificationExecutor<SysOperateLogPO> {

}