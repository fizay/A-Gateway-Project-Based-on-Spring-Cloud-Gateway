package com.fizay.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用该注解通过AOP环绕通知动态查询redis缓存
 * key为“/请求路径/用户/参数”,value为后台微服务响应结果
 * 
 * @author FUZIYAN	2020.8.10
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface Cache {
	String key() default "";
}
