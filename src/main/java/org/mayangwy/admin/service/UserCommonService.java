package org.mayangwy.admin.service;

import org.mayangwy.admin.dao.UserRepository;
import org.mayangwy.admin.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserCommonService {

    @Autowired
    private UserRepository userRepository;

    public List<UserPO> findAll(){
        //List<UserPO> all = userRepository.findAll();
        List<UserPO> all = userRepository.findAllById(Arrays.asList(3L, 4L));
        return all;
    }

    public void save(){
        UserPO userPO = new UserPO();
        userPO.setUserName("测试超长");
        userPO.setCreateTime(new Date());
        userPO.setCreateUserId(0L);
        userPO.setIsDel(0);
        userPO.setUpdateTime(new Date());
        userPO.setUpdateUserId(0L);
        userPO.setPassword("123321");
        userRepository.save(userPO);

        UserPO userPO2 = new UserPO();
        userPO2.setUserName("测试超长2");
        userPO2.setCreateTime(new Date());
        userPO2.setCreateUserId(0L);
        userPO2.setIsDel(0);
        userPO2.setUpdateTime(new Date());
        userPO2.setUpdateUserId(0L);
        userPO2.setPassword("123321");
        userRepository.save(userPO2);
    }

}