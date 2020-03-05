package org.mayangwy.admin.core.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mayangwy.admin.modules.system.entity.UserPO;

import java.util.Date;

public class Test {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        UserPO userPO = new UserPO();
        userPO.setUpdateTime(new Date());
        userPO.setCreateTime(new Date());
        userPO.setIsDel(0);
        userPO.setUpdateUserId(1122311313L);
        userPO.setId(111111L);
        userPO.setCreateUserId(1223232121L);
        userPO.setUserName("bhjwewwqjqwekjkqwjkj快就撒娇凯撒进口的撒娇" +
                "大数据那看啥dads健康大数据卡的是卡卡但" +
                "是啊的手机卡sands卡的撒数据库阿萨的基本凯撒的金卡的声卡的数据库撒娇" +
                "尽快把傻傻的接口大苏打撒旦看来撒可怜见杰卡斯打卡时间卡的手机卡的数据库打赏" +
                "啊的手机卡的手机卡第四季度萨卡昆达时间阿卡上的可靠度撒扩大临时绿卡的数量即可" +
                "案件本身的觉得撒娇的撒娇扩大进口大叔控啊是多久啊圣诞节暗杀的手机卡但是阿萨是卡卡是" +
                "和巴萨静安寺的几大势力扩大势力卡的数量撒卡卡罗的撒打算克拉金克拉大师金克拉撒旦就卡的手机卡的" +
                "是大家卡仕达的数据库科技大楼是会计lads就啊的深刻理解打卡记录深刻理解奥斯卡的就是啊圣诞快乐打开了时间" +
                "哈啥啊大厦大厦及暗杀的数据库大数据扩大按时间卡的时间空间卡刷道具卡进口的撒旦啊课件萨克就是" +
                "啊是多久啊是吉萨大家哈是多久啊距离喀什安康就是咯技术的凯撒的积极开朗的撒娇的奥克兰记录卡撒旦");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
//            String string = objectMapper.writeValueAsString(userPO);
            String string = JSON.toJSONString(userPO);
            string.trim();
        }
        System.out.println(System.currentTimeMillis() - startTime);

        String sss = null;
        System.out.println(sss instanceof String);

        System.out.println(StrUtils.getGetterName("a"));
    }

}
