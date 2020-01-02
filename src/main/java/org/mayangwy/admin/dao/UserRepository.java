package org.mayangwy.admin.dao;

import org.mayangwy.admin.entity.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {
}