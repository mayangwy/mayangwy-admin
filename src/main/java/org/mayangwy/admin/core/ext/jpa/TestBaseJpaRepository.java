package org.mayangwy.admin.core.ext.jpa;

import org.mayangwy.admin.core.base.entity.PageInput;
import org.mayangwy.admin.core.base.entity.PageOutput;
import org.mayangwy.admin.modules.system.entity.UserPO;
import org.mayangwy.admin.modules.system.entity.UserQueryDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestBaseJpaRepository extends BaseJpaRepository<UserPO> {

    public UserPO findOne(UserQueryDTO userQueryDTO){
        String hql = "select a from UserPO a where a.userName = :userName and a.isDel = :isDel";
        return findOne(hql, userQueryDTO);
    }

    public List<UserPO> findList(UserQueryDTO userQueryDTO){
        String hql = "select a from UserPO a where a.userName like :userNameLike and a.isDel = :isDel";
        return findList(hql, userQueryDTO);
    }

    public PageOutput<UserPO> findPage(UserQueryDTO userQueryDTO){
        String hql = "select a from UserPO a where a.userName like :userNameLike and a.isDel = :isDel";
        return findPage(hql, userQueryDTO, new PageInput());
    }

}