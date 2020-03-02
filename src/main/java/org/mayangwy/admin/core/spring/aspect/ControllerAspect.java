package org.mayangwy.admin.core.spring.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mayangwy.admin.core.base.annotation.ReqLog;
import org.mayangwy.admin.core.base.entity.RespResult;
import org.mayangwy.admin.core.base.enums.CommonErrorEnum;
import org.mayangwy.admin.core.base.exception.SystemRuntimeException;
import org.mayangwy.admin.core.utils.CommonUtils;
import org.mayangwy.admin.core.utils.ThreadLocals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class ControllerAspect {

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public void pointcut1() {

    }

    /*@Pointcut("(@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)) && @annotation(org.mayangwy.admin.core.base.annotation.ReqLog)")
    public void pointcut2(){

    }*/

    @Around(value = "pointcut1()")
    public Object around(ProceedingJoinPoint joinPoint) throws SystemRuntimeException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ReqLog reqLog = method.getAnnotation(ReqLog.class);

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("CONTROLLER {{}.{}} EXECUTE START", className, methodName);

        //记录请求参数
        boolean inputRecord = reqLog == null || reqLog.inputRecord();
        String inputStr = null;
        if (inputRecord) {
            inputStr = getInputStr(joinPoint);
            log.info("CONTROLLER {{}.{}} params : {}", className, methodName, inputStr);
        }

        String outputStr = null;
        Object result = null;
        //是否记录返回结果
        boolean outputRecord = reqLog != null && reqLog.outputRecord();
        //是否持久化到数据库
        boolean isPersist = reqLog == null || reqLog.isPersist();
        try {
            result = joinPoint.proceed();
            result = getOutput(joinPoint, result);

            if (outputRecord) {
                try {
                    if (ThreadLocals.isReturnJsonThreadLocal.get()) {
                        outputStr = objectMapper.writeValueAsString(result);
                    } else {
                        //遍历入参是否有Model、ModelMap、ModelAndView等，然后打印
                        if (result instanceof ModelAndView) {
                            ModelAndView mav = (ModelAndView) result;
                            outputStr = objectMapper.writeValueAsString(mav.getModelMap());
                        } else {
                            Object[] args = joinPoint.getArgs();
                            for (int i = 0; i < args.length; i++) {
                                if (args[i] == null) {
                                    continue;
                                }
                                if (args[i] instanceof Model) {
                                    outputStr = objectMapper.writeValueAsString(((Model) args[i]).asMap());
                                    break;
                                }

                                if (args[i] instanceof ModelMap) {
                                    outputStr = objectMapper.writeValueAsString(args[i]);
                                    break;
                                }

                                if (args[i] instanceof ModelAndView) {
                                    ModelAndView modelAndView = ((ModelAndView) args[i]);
                                    outputStr = objectMapper.writeValueAsString(modelAndView.getModelMap());
                                    break;
                                }

                            }
                        }
                    }
                } catch (JsonProcessingException ignored) {
                    outputStr = result.toString();
                }

                log.info("CONTROLLER {{}.{}} result : {}", className, methodName, outputStr);
            }

            logRecord(joinPoint, isPersist, inputStr, outputStr, null);
        } catch (Throwable throwable) {
            //错误的时候，强制打印入参，但是打印过的话就不用再打印了。
            if (!inputRecord) {
                String inputStrTemp = getInputStr(joinPoint);
                log.info("CONTROLLER {{}.{}} params : {}", className, methodName, inputStrTemp);
            }

            SystemRuntimeException systemRuntimeException;
            if (throwable instanceof SystemRuntimeException) {
                systemRuntimeException = (SystemRuntimeException) throwable;
            } else {
                systemRuntimeException = new SystemRuntimeException(CommonErrorEnum.FAIL, throwable);
            }

            log.error("", systemRuntimeException);

            logRecord(joinPoint, isPersist, inputStr, null, systemRuntimeException);

            throw systemRuntimeException;
        }


        return result;
    }

    private void logRecord(ProceedingJoinPoint joinPoint, boolean isPersist, String inputStr, String outputStr, SystemRuntimeException systemRuntimeException) {
        Long startTime = ThreadLocals.startTimeThreadLocal.get();

        Long endTime = System.nanoTime();
        Long menuId = 1L;
        String traceId = ThreadLocals.traceIdThreadLocal.get();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        if (systemRuntimeException == null) {
            log.info("CONTROLLER {{}.{}} EXECUTE SUCCESS, COST TIME : {}", "HTTP." + className, methodName, CommonUtils.calculateCostTime(startTime, endTime));
        } else {
            log.info("CONTROLLER {{}.{}} EXECUTE FAIL, COST TIME : {}", "HTTP." + className, methodName, CommonUtils.calculateCostTime(startTime, endTime));
        }

    }

    private Object getOutput(ProceedingJoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Boolean isReturnJson = ThreadLocals.isReturnJsonThreadLocal.get();
        String traceId = ThreadLocals.traceIdThreadLocal.get();
        if (isReturnJson) {
            if (result instanceof RespResult) {
                ((RespResult) result).setTraceId(traceId);
            }
            return result;
        } else {
            //是否可以直接在request中设置traceId
            if (result instanceof ModelAndView) {
                ModelAndView mav = (ModelAndView) result;
                mav.getModelMap().addAttribute("traceId", traceId);
            } else {
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < args.length; i++) {
                    if (args[i] == null) {
                        continue;
                    }
                    if (args[i] instanceof Model) {
                        ((Model) args[i]).addAttribute("traceId", traceId);
                        break;
                    }

                    if (args[i] instanceof ModelMap) {
                        ((ModelMap) args[i]).addAttribute("traceId", traceId);
                        break;
                    }

                    if (args[i] instanceof ModelAndView) {
                        ModelAndView modelAndView = ((ModelAndView) args[i]);
                        modelAndView.getModelMap().addAttribute("traceId", traceId);
                        break;
                    }

                }
            }

            return result;
        }

    }

    private String getInputStr(ProceedingJoinPoint joinPoint) {
        //Annotation[][] parameterAnnotations = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        List<Map<String, Object>> argList = new ArrayList<>(argNames.length * 2);
        for (int i = 0; i < args.length; i++) {
            Map<String, Object> argMap = new HashMap<>();
            if (args[i] != null) {
                if (!(args[i] instanceof ServletRequest
                        || args[i] instanceof ServletResponse
                        || args[i] instanceof Model
                        || args[i] instanceof ModelMap
                        || args[i] instanceof ModelAndView)) {
                    try {
                        argMap.put(argNames[i], objectMapper.writeValueAsString(args[i]));
                    } catch (JsonProcessingException e) {
                        argMap.put(argNames[i], args[i].toString());
                    }
                } else {
                    argMap.put(argNames[i], args[i].toString());
                }
            } else {
                argMap.put(argNames[i], "null");
            }
            argList.add(argMap);
        }
        return argList.toString();
    }

}