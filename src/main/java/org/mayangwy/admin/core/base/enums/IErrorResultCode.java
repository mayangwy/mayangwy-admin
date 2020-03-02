package org.mayangwy.admin.core.base.enums;

/**
 *
 * 错误类结果码枚举类需要实现的接口规范
 *
 */
public interface IErrorResultCode {

    Integer getResultCode();

    String getMsg();

    Integer getErrorLevel();

}