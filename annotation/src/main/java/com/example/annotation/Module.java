package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YETLAND
 * @date 2018/9/29 15:25
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Module {
}
