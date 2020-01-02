package org.mayangwy.admin.service;

import org.mayangwy.admin.dao.UserRepository;
import org.mayangwy.admin.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommonService {

    @Autowired
    private UserRepository userRepository;

    public List<UserPO> findAll(){
        List<UserPO> all = userRepository.findAll();
        return all;
    }

}