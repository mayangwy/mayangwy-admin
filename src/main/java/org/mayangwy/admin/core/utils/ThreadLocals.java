package org.mayangwy.admin.core.utils;

import java.util.Date;

public class ThreadLocals {

    public static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
    public static final ThreadLocal<Date> startDateThreadLocal = new ThreadLocal<>();

    public static final ThreadLocal<String> traceIdThreadLocal = new ThreadLocal<>();

    public static final ThreadLocal<Boolean> isSuccessReqThreadLocal = new ThreadLocal<>();

    public static final ThreadLocal<Boolean> isReturnJsonThreadLocal = new ThreadLocal<>();

}