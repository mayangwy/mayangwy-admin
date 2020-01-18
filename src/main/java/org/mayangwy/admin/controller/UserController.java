package org.mayangwy.admin.controller;

import org.mayangwy.admin.entity.UserPO;
import org.mayangwy.admin.service.UserCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
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

    @GetMapping("/b")
    public ResponseEntity.BodyBuilder save(){
        userCommonService.save();
        return ResponseEntity.ok();
    }

    @PostMapping("/c")
    public void testDate(@RequestBody UserPO userPO){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(userPO.getCreateTime()));
        System.out.println(simpleDateFormat.format(userPO.getUpdateTime()));
    }

}