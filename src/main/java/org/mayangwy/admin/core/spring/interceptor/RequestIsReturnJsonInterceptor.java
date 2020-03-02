package org.mayangwy.admin.core.spring.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.mayangwy.admin.core.utils.ThreadLocals;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class RequestIsReturnJsonInterceptor implements HandlerInterceptor {

    /**
     *
     * 之所以选择在拦截器中设置，是因为如果在aop中设置，进入aop之前就发生异常的话，无法得知请求是普通返回还是返回json
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            RestController annotationRestController = bean.getClass().getAnnotation(RestController.class);
            ResponseBody responseBodyAnnotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
            if(annotationRestController != null || responseBodyAnnotation != null){
                ThreadLocals.isReturnJsonThreadLocal.set(true);
            } else {
                ThreadLocals.isReturnJsonThreadLocal.set(false);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}