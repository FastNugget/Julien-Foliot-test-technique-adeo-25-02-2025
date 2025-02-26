package com.yaourt.exception.generic;

import com.yaourt.exception.ErrorModelEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ErrorModelType {

    // -- IMPL ---------------------------------------------------------------------------------------------------------

    ErrorModelEnum errorModelEnum() default ErrorModelEnum.EXCEPTION_GENERIC;

}
