package org.mayangwy.admin.core.base.enums;

import lombok.Getter;

@Getter
public enum ErrorLevelEnum {

    //ErrorLevel为0时打印WARN级别日志，为1时打印ERROR级别日志
    WARN(0, "警告"), ERROR(1, "严重");

    private Integer value;
    private String desc;

    private ErrorLevelEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}