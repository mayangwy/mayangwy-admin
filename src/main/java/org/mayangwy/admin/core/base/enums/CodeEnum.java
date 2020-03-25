package org.mayangwy.admin.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(0, "成功"), FAIL(1, "失败"), NOAUTH(2, "认证已失效");

    private Integer code;

    private String desc;

}