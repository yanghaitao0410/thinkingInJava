package com.yht.extension;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClassScanHandler {
    private static Reflections reflections;

    static {
        //构建扫描器
        ConfigurationBuilder cb = ConfigurationBuilder.build(
                ExtensionConstant.COMPONENT_PACKAGE,
                new SubTypesScanner(true),
                new TypeAnnotationsScanner());
        reflections = new Reflections(cb);
    }

    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation, true);
    }

}
