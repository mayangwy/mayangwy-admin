package org.mayangwy.admin.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDelEnum {

    YES(0, "未删除"), NO(1, "已删除");

    private Integer code;
    private String name;

}