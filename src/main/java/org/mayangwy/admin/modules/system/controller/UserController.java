package org.mayangwy.admin.modules.system.controller;

import org.mayangwy.admin.core.base.enums.CommonErrorEnum;
import org.mayangwy.admin.core.base.exception.SystemRuntimeException;
import org.mayangwy.admin.modules.system.entity.UserPO;
import org.mayangwy.admin.modules.system.service.UserCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserCommonService userCommonService;

    @Autowired
    private DataSource dataSource;

    @Value("${a:0}")
    private Integer a;

    @GetMapping("/a")
    public List<UserPO> findAll(){
        //throw new RuntimeException("aaabbb");
        //throw new SystemRuntimeException(CommonResultCodeEnum.FAIL);
        //System.out.println(a);
        return userCommonService.findAll();
    }

    @GetMapping("/b")
    public ResponseEntity.BodyBuilder save(){
        userCommonService.save();
        return ResponseEntity.ok();
    }

    @PostMapping("/c")
    public void testDate(@RequestBody UserPO userPO){
        throw new SystemRuntimeException(CommonErrorEnum.FAIL);
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(userPO.getCreateTime()));
        System.out.println(simpleDateFormat.format(userPO.getUpdateTime()));*/
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