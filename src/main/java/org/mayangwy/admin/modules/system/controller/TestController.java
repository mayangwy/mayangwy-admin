package org.mayangwy.admin.modules.system.controller;

import org.mayangwy.admin.core.base.annotation.ReqLog;
import org.mayangwy.admin.core.base.entity.RespResult;
import org.mayangwy.admin.core.base.enums.CommonSuccessEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private static final List<String> stringList = new ArrayList<>();

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

}