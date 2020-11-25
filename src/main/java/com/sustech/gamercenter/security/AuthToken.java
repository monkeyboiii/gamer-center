package com.sustech.gamercenter.security;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {

    String requiredRole() default "";

    @AliasFor(value = "requiredRole")
    String role() default "";

    String requiredPermission() default "";

    @AliasFor(value = "requiredPermission")
    String permission() default "";

    // TODO add role/permission based authorization
}
