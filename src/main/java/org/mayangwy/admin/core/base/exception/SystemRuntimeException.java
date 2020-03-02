package org.mayangwy.admin.core.base.exception;

import lombok.Data;
import org.mayangwy.admin.core.base.enums.IErrorResultCode;

@Data
public class SystemRuntimeException extends RuntimeException {

    private IErrorResultCode result;

    private Object data;

    public SystemRuntimeException(IErrorResultCode result) {
        this(result, null);
    }

    public SystemRuntimeException(IErrorResultCode result, Object data) {
        this(result, data, null);
    }

    public SystemRuntimeException(IErrorResultCode result, Object data, Throwable e) {
        super(result.getMsg(), e);
        this.result = result;
        this.data = data;
    }

}