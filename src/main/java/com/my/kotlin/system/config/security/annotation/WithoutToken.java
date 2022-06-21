package com.my.kotlin.system.config.security.annotation;

import java.lang.annotation.*;

/**
 * Security允许匿名访问
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithoutToken {
}