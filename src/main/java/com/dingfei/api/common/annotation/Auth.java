package com.dingfei.api.common.annotation;

import java.lang.annotation.*;

/**
 * Auth
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    String value() default "";
    int action() default 0;
    boolean authorize() default true;
    int type() default 1;
}
