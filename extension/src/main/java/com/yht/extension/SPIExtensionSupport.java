package com.yht.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SPIExtensionSupport {


    /**
     * 缓存基础类：业务身份：扩展实现名称的映射关系
     */
    private Map<Class<?>, Map<String, String>> base2Identity2ExtensionNameMap = new ConcurrentHashMap<>();

    /**
     * 扩展实现名称与实例类的映射关系
     */
    private Map<String, Class> name2ClassMap = new ConcurrentHashMap<>();

    private static SPIExtensionSupport instance = new SPIExtensionSupport();

    /**
     * @return 单态实例
     */
    public static SPIExtensionSupport getInstance() {
        return instance;
    }

    private SPIExtensionSupport() {
        Set<Class<?>> extensionClass = ClassScanHandler.getTypesAnnotatedWith(BusinessExtension.class);
        for (Class<?> claz : extensionClass) {
            BusinessExtension businessExtensionAnnotation = claz.getAnnotation(BusinessExtension.class);
            if (businessExtensionAnnotation == null) {
                continue;
            }
            Class baseClass = businessExtensionAnnotation.baseClass();
            String[] identitys = businessExtensionAnnotation.identity();
            String name = claz.getName();
            Map<String, String> identity2ExtensionNameMap = base2Identity2ExtensionNameMap.get(baseClass);
            if (identity2ExtensionNameMap == null) {
                identity2ExtensionNameMap = new ConcurrentHashMap<String, String>();
                base2Identity2ExtensionNameMap.put(baseClass, identity2ExtensionNameMap);
            }
            for (String identity : identitys) {
                if (identity2ExtensionNameMap.containsKey(identity)) {
                    log.error("extension class exist duplicate, name:{}, identity:{}, existClassName:{}", name,
                            identity, identity2ExtensionNameMap.get(identity));
                    throw new RuntimeException();
                }
                identity2ExtensionNameMap.put(identity, name);
            }
            name2ClassMap.put(name, claz);
        }
    }

    /**
     * 获取所有扩展基础类
     *
     * @return
     */
    public Set<Class<?>> getAllBaseClasses() {
        return base2Identity2ExtensionNameMap.keySet();
    }

    /**
     * 获取扩展类的自定义名称
     * @param baseClass 基础类
     * @param identity 业务身份
     * @return
     */
    public String getExtensionName(Class baseClass, String identity) {
        if (identity == null) {
            return null;
        }
        Map<String, String> identity2Name = base2Identity2ExtensionNameMap.get(baseClass);
        if (CollectionUtils.isEmpty(identity2Name)) {
            return null;
        }

        String extensionName = identity2Name.get(identity);
        if (extensionName == null) {
            extensionName = identity2Name.get(identity);
        }

        if (extensionName != null){
            return extensionName;
        }

        List<String> allDefaultIdentitys = BusinessIdentityUtil.getAllDefaultIdentity(identity);
        for (String defaultIdentity : allDefaultIdentitys){
            if (identity2Name.containsKey(defaultIdentity)){
                extensionName =  identity2Name.get(defaultIdentity);
                return extensionName;
            }
        }

        return extensionName;
    }

    /**
     * 获取扩展实例
     *
     * @param name 实例类的自定义名称
     * @return
     */
    public Class<?> getExtensionClass(String name) {
        return name2ClassMap.get(name);
    }

}
