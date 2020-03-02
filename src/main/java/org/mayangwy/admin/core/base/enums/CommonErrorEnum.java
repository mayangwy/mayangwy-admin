package org.mayangwy.admin.core.base.enums;

import lombok.Getter;

@Getter
public enum CommonErrorEnum implements IErrorResultCode {

    FAIL(2000, "服务器内部异常", 1),
    FAIL_AUTH_EXPIRE(2001, "认证过期，请重新登陆", 1);

    private Integer resultCode;
    private String msg;
    private Integer errorLevel;

    CommonErrorEnum(Integer resultCode, String msg, Integer errorLevel) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.errorLevel = errorLevel;
    }

}