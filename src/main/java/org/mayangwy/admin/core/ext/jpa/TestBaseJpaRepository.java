package org.mayangwy.admin.core.ext.jpa;

import org.mayangwy.admin.modules.system.entity.UserPO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestBaseJpaRepository extends BaseJpaRepository<UserPO> {

    public List<UserPO> findList(){
        String hql = "select id, userName from UserPO";
        List<UserPO> userPOS = find(hql, UserPO.class);
        System.out.println();
        return userPOS;
    }

}