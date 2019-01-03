package com.dongyu.company.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录操作对应的字段的中文
 *
 * @author TYF
 * @date 2019/1/3
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttributeOpName {

    /**
     * 需要记录的字段名称
     *
     * @return
     */
    String value() default "";
}
