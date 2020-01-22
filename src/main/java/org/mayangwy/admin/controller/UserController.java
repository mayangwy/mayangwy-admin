package org.mayangwy.admin.controller;

import org.mayangwy.admin.entity.UserPO;
import org.mayangwy.admin.service.UserCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/d")
    public void testSet(@RequestParam("id") Set<Long> ids){
        System.out.println(ids.size());
    }

    @GetMapping("/e")
    public void testE(){
        userCommonService.testTransaction();
    }

}