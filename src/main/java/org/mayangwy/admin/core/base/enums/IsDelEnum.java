package org.mayangwy.admin.core.base.enums;

import lombok.Getter;

@Getter
public enum IsDelEnum {

    YES(0, "未删除"), NO(1, "已删除");

    private Integer code;
    private String name;

    IsDelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}