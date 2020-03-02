package org.mayangwy.admin.core.utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

    public static String calculateCostTime(Long startTime, Long endTime){
        if(ObjectUtil.hasEmpty(startTime, endTime)){
            return null;
        } else {
            long costTime = (endTime - startTime) / (1000 * 1000);
            return NumberUtil.decimalFormat("#0.00", costTime) + " ms";
        }
    }

    public static boolean isCommonReq(){
        HttpServletRequest request = ServletRequestUtils.getRequest();
        String accept = request.getHeader("accept");
        return accept.equalsIgnoreCase("text/html");
    }

}