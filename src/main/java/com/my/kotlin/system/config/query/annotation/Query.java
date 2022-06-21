package com.my.kotlin.system.config.query.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Author pangjie
 * @Description //TODO 查询方式注解
 * @Date 15:54 2020/10/14 0014
 * @Param
 * @return
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    //属性名称
    String propName() default "";

    //默认查询方式
    Type type() default Type.EQUAL;


    enum Type {
        //相等
        EQUAL,
        //不相等
        NOT_EQUAL,
        //包含
        IN,
        //不包含
        NOT_IN,
        //等于空
        IS_NULL,
        //不等于空
        NOT_NULL,
        //之间
        BETWEEN,
        // >=
        GREATER_THEN,
        // <=
        LESS_THEN,
        // >
        GREATER_THEN_N_Q,
        // <
        LESS_THEN_N_Q,
        //模糊
        INNER_LIKE
    }
}
