package com.yht.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Component
@Slf4j
public class BeanSupport implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    /**
     * 注入依赖spring bean
     * @param beans 待注入依赖的bean集合
     */
    public void autowireBeans(Collection<Object> beans) {
        if (CollectionUtils.isEmpty(beans)) {
            return;
        }
        beans.stream().forEach(e -> autowireBean(e));
    }

    /**
     * 注入依赖spring bean
     * @param bean 待注入依赖的bean
     */
    public void autowireBean(Object bean) {
        if (bean == null) {
            return;
        }
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    /**
     * 注入依赖spring bean
     *
     * @param bean 待注入依赖的bean
     */
    public static void autowireBean(ApplicationContext applicationContext, Object bean) {
        if (bean == null) {
            return;
        }
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    /**
     * 创建实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(ApplicationContext applicationContext,Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            T object = clazz.newInstance();
            autowireBean(applicationContext, object);
            return object;
        } catch (Exception e) {
            log.error("init object error.", e);
        }
        return null;
    }


    /**
     * 创建实例
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T newInstance(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            T object = null;
            BusinessExtension businessExtensionAnnotation = clazz.getAnnotation(BusinessExtension.class);
            if (businessExtensionAnnotation != null) {
                Class baseClass = businessExtensionAnnotation.baseClass();
                IdentityDO identity = LocalCache.getIdentity();
                if (identity == null || baseClass == null) {
                    return null;
                }
                SPIExtensionSupport spiExtensionSupport = SPIExtensionSupport.getInstance();
                String extensionName = spiExtensionSupport.getExtensionName(baseClass, identity.toBizCodeString());
                Class actualClass = spiExtensionSupport.getExtensionClass(extensionName);
                object = (T)actualClass.newInstance();
            } else {
                object = clazz.newInstance();
            }
            autowireBean(object);
            return object;
        } catch (Exception e) {
            log.error("init object error.", e);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanSupport.applicationContext = applicationContext;

        // 将自身注入缓存，方便使用
        LocalCache.setBeanSupport(this);
    }

    public static ApplicationContext getApplicationContext(){
        return BeanSupport.applicationContext;
    }

    public static <T>T getBean(Class<T> beanClass){
        return BeanSupport.applicationContext.getBean(beanClass);
    }
}
