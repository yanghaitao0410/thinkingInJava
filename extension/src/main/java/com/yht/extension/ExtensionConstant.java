package com.yht.extension;

import com.google.common.collect.Sets;

import java.util.Set;

public class ExtensionConstant {
    /**
     * extension 扫描扩展点实现类路径
     * 使用需要在项目启动类中，把实际项目中的路径添加到这个set中
     */
    public static final Set<String> COMPONENT_PACKAGE = Sets.newHashSet("com.yht.extension");

}
