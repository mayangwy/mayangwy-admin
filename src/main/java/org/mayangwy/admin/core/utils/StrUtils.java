package org.mayangwy.admin.core.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class StrUtils {

    /**
     *
     * 获取属性的setter方法名称
     * @param fieldName
     * @return
     *
     */
    public static String getSetterName(String fieldName){
        boolean b = StrUtil.isBlank(fieldName);
        Validator.validateFalse(b, "fieldName is not blank");

        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     *
     * 获取属性的getter方法名称
     * @param fieldName
     * @return
     *
     */
    public static String getGetterName(String fieldName){
        boolean b = StrUtil.isBlank(fieldName);
        Validator.validateFalse(b, "fieldName is not blank");

        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}