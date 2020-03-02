package org.mayangwy.admin.core.spring.handler;

import lombok.extern.slf4j.Slf4j;
import org.mayangwy.admin.core.base.entity.RespResult;
import org.mayangwy.admin.core.base.enums.CommonErrorEnum;
import org.mayangwy.admin.core.base.exception.SystemRuntimeException;
import org.mayangwy.admin.core.utils.ThreadLocals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class WebMvcExceptionHandler {

    @ExceptionHandler({SystemRuntimeException.class})
    public Object handleSystemRuntimeException(SystemRuntimeException exception){
        ThreadLocals.isSuccessReqThreadLocal.set(false);

        //交给AOP打印错误日志
        //log.error(exception.getMessage(), exception);
        if(ThreadLocals.isReturnJsonThreadLocal.get()){
            RespResult<?> respResult = RespResult.fail(exception.getResult(), exception.getData());
            respResult.setTraceId(ThreadLocals.traceIdThreadLocal.get());

            return new ResponseEntity<>(respResult, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", exception.getResult().getMsg());
            modelAndView.addObject("data", exception.getData());
            modelAndView.setViewName("error");
            return modelAndView;
            //throw exception;
        }
    }

    @ExceptionHandler({Exception.class})
    public Object handleException(Exception exception) throws Exception {
        ThreadLocals.isSuccessReqThreadLocal.set(false);

        log.error(exception.getMessage(), exception);

        if(ThreadLocals.isReturnJsonThreadLocal.get()) {
            RespResult<Object> respResult = RespResult.fail(CommonErrorEnum.FAIL, null);
            respResult.setTraceId(ThreadLocals.traceIdThreadLocal.get());
            respResult.setMsg(exception.getMessage());

            return new ResponseEntity<>(respResult, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", exception.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
            //throw ex;
        }
    }

}