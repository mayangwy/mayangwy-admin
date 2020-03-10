package org.mayangwy.admin.modules.system.controller;

import org.mayangwy.admin.core.base.annotation.ReqLog;
import org.mayangwy.admin.core.base.entity.RespResult;
import org.mayangwy.admin.core.base.enums.CommonSuccessEnum;
import org.mayangwy.admin.core.ext.jpa.TestBaseJpaRepository;
import org.mayangwy.admin.modules.system.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class TestController {

    private static final List<String> stringList = new ArrayList<>();

    @Autowired
    private TestBaseJpaRepository testBaseJpaRepository;

    @RequestMapping("/test")
    public String test(String aaa) {
        throw new RuntimeException("我异常了");
        //return "aaa";
    }

    @RequestMapping("/test2")
    @ResponseBody
    @ReqLog(outputRecord = true)
    public RespResult<Map<String, Object>> test2() {
        /*for(int i = 0; i < 1000000000; i++){
            stringList.add("我我我我我我我我我我我我我我我我我我我我我我我我我我我强强强强强强强强五千万亲请问请问请问请问恰恰我");
        }*/
        System.out.println(stringList.size());
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "111");
        return RespResult.success(CommonSuccessEnum.SUCCESS_OPERATE, map);
    }

    @RequestMapping("/test3")
    @ResponseBody
    @ReqLog(outputRecord = true)
    public Map<String, Object> test3(String a, Integer i) {
        System.out.println(stringList.size());
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "111");
        return map;
    }

    @GetMapping("/test4")
    @ResponseBody
    public RespResult<List<String>> test4(){
        return RespResult.success(Arrays.asList("123", "456"));
    }

    @GetMapping("/testFindById")
    @ResponseBody
    public RespResult<List<String>> testFindById(){
        List<UserPO> all = testBaseJpaRepository.findList();
        return RespResult.success(Arrays.asList("123", "456"));
    }

}