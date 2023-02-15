package com.simple.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author FanXing
 * @date 2023年02月15日 22:56
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Simpler {
    String prefix() default "";
}
