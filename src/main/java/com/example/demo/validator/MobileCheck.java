package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * MobileCheck class
 *
 * @author maco
 * @data 2019/10/24
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface MobileCheck {
    /**
     * @Retention:用来说明该注解类的生命周期，有三个参数
     * RetentionPolicy.SOURCE: 注解只保留在源文件种
     * RetentionPolicy.CLASS: 注解保留在class文件中，在加载到JVM虚拟机时丢弃
     * RetentionPolicy.RUNTIME: 注解保留在程序运行期间，此时可以通过反射获得定义在某个类上的所有注解
     */
    boolean required() default true;

    String message() default "手机号码格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
