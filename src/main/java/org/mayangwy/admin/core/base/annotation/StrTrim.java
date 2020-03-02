package org.mayangwy.admin.core.base.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mayangwy.admin.core.ext.jackson.deserializer.StrTrimDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@JacksonAnnotationsInside
@JsonDeserialize(using = StrTrimDeserializer.class)
public @interface StrTrim {

    boolean nullToEmptyStr() default false;

}