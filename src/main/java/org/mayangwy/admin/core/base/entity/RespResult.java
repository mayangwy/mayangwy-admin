package org.mayangwy.admin.core.base.entity;

import lombok.Data;
import org.mayangwy.admin.core.base.enums.CommonSuccessEnum;
import org.mayangwy.admin.core.base.enums.IErrorResultCode;
import org.mayangwy.admin.core.base.enums.ISuccessResultCode;

/**
 * 返回结果实体类 create at 2020/2/12
 */
@Data
public class RespResult<T> {

    private Boolean isSuccess;

    private Integer resultCode; //请求结果码，一般情况下每个结果码对应一个提示消息

    private String msg; //提示消息

    private T data; //响应结果

    //可能需要一个errorDetail用于返回除了异常级别等异常信息
    private Integer errorLevel;

    private String traceId; //每个请求对应的编号，用于日志追踪

    private RespResult() {

    }

    public static <D> RespResult<D> success(D data) {
        return success(CommonSuccessEnum.SUCCESS, data);
    }

    public static <D> RespResult<D> success(ISuccessResultCode resultCode, D data) {
        return success(resultCode.getResultCode(), resultCode.getMsg(), data);
    }

    public static <D> RespResult<D> success(Integer resultCode, String msg, D data) {
        RespResult<D> respResult = new RespResult<>();
        respResult.setIsSuccess(true);
        respResult.setResultCode(resultCode);
        respResult.setMsg(msg);
        respResult.setData(data);
        return respResult;
    }

    public static <D> RespResult<D> fail(IErrorResultCode resultCode) {
        return fail(resultCode, null);
    }

    public static <D> RespResult<D> fail(IErrorResultCode resultCode, D data) {
        RespResult<D> respResult = new RespResult<>();
        respResult.setIsSuccess(false);
        respResult.setResultCode(resultCode.getResultCode());
        respResult.setMsg(resultCode.getMsg());
        respResult.setData(data);
        respResult.setErrorLevel(respResult.getErrorLevel());
        return respResult;
    }

}