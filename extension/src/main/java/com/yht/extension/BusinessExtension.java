package com.yht.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessExtension {

    //需要替换注入的业务基础类
    public Class baseClass();

    //需要执行该注解的业务身份名称
    public String[] identity();
}
