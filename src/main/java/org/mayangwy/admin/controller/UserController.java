package org.mayangwy.admin.controller;

import org.mayangwy.admin.entity.UserPO;
import org.mayangwy.admin.service.UserCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserCommonService userCommonService;

    @Autowired
    private DataSource dataSource;

    @GetMapping("/a")
    public List<UserPO> findAll(){
        return userCommonService.findAll();
    }

}