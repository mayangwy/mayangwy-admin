package org.mayangwy.admin.core.base.enums;

import lombok.Getter;

@Getter
public enum CodeEnum {

    SUCCESS(0, "成功"), FAIL(1, "失败"), NOAUTH(2, "认证已失效");

    private Integer code;

    private String desc;

    private CodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}