package org.mayangwy.admin.core.base.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mayangwy.admin.core.ext.jackson.serializer.BigDecimalFormatSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@JacksonAnnotationsInside
@JsonSerialize(using = BigDecimalFormatSerializer.class)
public @interface BigDecimalToFormatStr {
    
    String pattern() default "#0.00";

    RoundingMode roundingMode() default RoundingMode.HALF_UP;

}