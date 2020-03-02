package org.mayangwy.admin.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ServletRequestUtils {

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static String getControllerPath(){
        return getControllerPath(getRequest());
    }

    public static String getControllerPath(HttpServletRequest request){
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        if(contextPath != null){
            return requestURI.substring(contextPath.length());
        }
        return requestURI;
    }

    public static String getMethod(){
        return getRequest().getMethod();
    }

}