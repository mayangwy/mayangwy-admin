package org.mayangwy.admin.core.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author mayang create at 2020/2/21
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqLog {

    boolean isPersist() default true;

    boolean inputRecord() default true;

    boolean outputRecord() default false;

}