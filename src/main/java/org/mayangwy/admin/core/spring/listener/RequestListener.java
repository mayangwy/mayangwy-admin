package org.mayangwy.admin.core.spring.listener;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.mayangwy.admin.core.utils.ServletRequestUtils;
import org.mayangwy.admin.core.utils.ThreadLocals;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@WebListener
@Slf4j
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ThreadLocals.startTimeThreadLocal.set(System.nanoTime());
        ThreadLocals.startDateThreadLocal.set(new Date());
        ThreadLocals.isSuccessReqThreadLocal.set(true);

        ServletRequest servletRequest = sre.getServletRequest();
        String traceId;
        if(servletRequest instanceof HttpServletRequest){
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            traceId = request.getHeader("traceId");
            if(StrUtil.isBlank(traceId)){
                traceId = IdUtil.simpleUUID();
            }

            ThreadContext.put("logId", traceId);
            ThreadLocals.traceIdThreadLocal.set(traceId);

            log.info("REQUEST IS START, HTTP.METHOD : {}, URL : {}", request.getMethod(), ServletRequestUtils.getControllerPath(request));
        } else {
            traceId = IdUtil.simpleUUID();

            ThreadContext.put("logId", traceId);
            ThreadLocals.traceIdThreadLocal.set(traceId);

            log.info("REQUEST IS START, NO HttpServletRequest");
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        Boolean isSuccess = ThreadLocals.isSuccessReqThreadLocal.get();
        String result;
        if(isSuccess != null && isSuccess){
            result = "SUCCESS";
        } else {
            result = "FAIL";
        }
        ServletRequest servletRequest = sre.getServletRequest();
        if(servletRequest instanceof HttpServletRequest){
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            log.info("REQUEST IS {}, HTTP.METHOD : {}, URL : {}", result, request.getMethod(), ServletRequestUtils.getControllerPath(request));
        } else {
            log.info("REQUEST IS {}, URL : NO HttpServletRequest", result);
        }

        ThreadLocals.startTimeThreadLocal.remove();
        ThreadLocals.startDateThreadLocal.remove();
        ThreadLocals.traceIdThreadLocal.remove();
        ThreadLocals.isSuccessReqThreadLocal.remove();
        //以下方式不是更好更能释放内存么？
        //ThreadLocals.isSuccessReqThreadLocal.set(null);
    }

}