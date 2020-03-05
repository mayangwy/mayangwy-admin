package org.mayangwy.admin.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 *
 * ServletRequest工具类   create at 2020/3/5
 * @author mayang
 *
 */
public class ServletRequestUtils {

    /**
     *
     * 获取HttpServletRequest对象
     * @return
     *
     */
    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     *
     * 获取本次执行的controller方法所配置的url
     * @return
     *
     */
    public static String getControllerPath(){
        return getControllerPath(getRequest());
    }

    /**
     * 获取本次请求执行的controller方法所配置的url
     * @param request
     * @return
     *
     */
    public static String getControllerPath(HttpServletRequest request){
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        if(contextPath != null){
            return requestURI.substring(contextPath.length());
        }
        return requestURI;
    }

    /**
     *
     * 获取本次请求的HTTP Method名称
     * @return
     *
     */
    public static String getMethod(){
        return getRequest().getMethod();
    }

}