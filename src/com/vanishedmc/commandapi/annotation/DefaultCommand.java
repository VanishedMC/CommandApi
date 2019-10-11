package com.vanishedmc.commandapi.annotation;

import com.vanishedmc.commandapi.AllowedSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultCommand {

    String permission() default "";
    AllowedSender allowedSender() default AllowedSender.BOTH;

}
