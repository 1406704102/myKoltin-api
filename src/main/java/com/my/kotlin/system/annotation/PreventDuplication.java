package com.my.kotlin.system.annotation;

import java.lang.annotation.*;

/*
 * @Author PangJie___
 * @Description //TODO 防止重复提交注解
 * @Date 下午2:34 5/7/2022
 * param 
 * return 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreventDuplication {
    /**
     * 防重复操作限时标记数值（存储redis限时标记数值）
     */
    String value() default "value" ;

    /**
     * 防重复操作过期时间（借助redis实现限时控制）
     */
    long expireSeconds() default 10;
}