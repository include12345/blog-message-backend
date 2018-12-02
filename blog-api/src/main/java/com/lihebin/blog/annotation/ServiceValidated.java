package com.lihebin.blog.annotation;

import java.lang.annotation.*;

/**
 * 使用在接口定义上，注解作用：接口方法参数校验.
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceValidated {

    Class<?>[] value() default {};

}
