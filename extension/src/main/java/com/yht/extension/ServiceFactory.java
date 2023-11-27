package com.yht.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactory<T> implements FactoryBean<T>, ApplicationContextAware {

    private Class<T> interfaceType;

    /**
     * 扩展实现名称与实例的映射关系
     */
    private Map<String, Object> name2InstanceMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    public ServiceFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {

        if (interfaceType.isInterface()) {
            InvocationHandler handler = new ServiceProxy<>(interfaceType, this);
            return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                    new Class[] {interfaceType}, handler);
        } else {
            SimpleInterceptor interceptor = new SimpleInterceptor();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(interfaceType);
            enhancer.setCallback((MethodInterceptor)(o, method, objects, methodProxy) -> {
                return interceptor.invoke(method, objects);
            });
            return (T)enhancer.create();
        }
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Object getExtensionActualInstance(){
        String identity = LocalCache.getIdentity().toBizCodeString();
        String name = SPIExtensionSupport.getInstance().getExtensionName(interfaceType, identity);
        if (name == null){
            return null;
        }
        if (!name2InstanceMap.containsKey(name)) {
            Class<?> claz = SPIExtensionSupport.getInstance().getExtensionClass(name);
            if (claz == null){
                return null;
            }
            name2InstanceMap.put(name, BeanSupport.newInstance(applicationContext, claz));
        }
        Object actualInstance = name2InstanceMap.get(name);
        return actualInstance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private class SimpleInterceptor {

        public Object invoke(Method method, Object[] objects) throws Throwable {
            Object actualInstance = getExtensionActualInstance();
            if (actualInstance == null){
                return null;
            }
            return method.invoke(actualInstance, objects);
        }
    }
}
