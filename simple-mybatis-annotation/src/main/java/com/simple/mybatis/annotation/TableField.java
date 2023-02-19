package com.simple.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author FanXing
 * @date 2023年02月15日 22:53
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    String value();
}
