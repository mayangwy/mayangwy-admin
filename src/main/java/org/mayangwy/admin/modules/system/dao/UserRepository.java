package org.mayangwy.admin.modules.system.dao;

import org.mayangwy.admin.modules.system.entity.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {

    @Query(nativeQuery = true, value = "select user_name from sys_user where id = ?1")
    String testNative(Long id);

}