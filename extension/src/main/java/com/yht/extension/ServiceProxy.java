package com.yht.extension;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceProxy<T> implements InvocationHandler {

    private Class<T> interfaceType;

    private ServiceFactory serviceFactory;


    public ServiceProxy(Class<T> interfaceType, ServiceFactory serviceFactory) {
        this.interfaceType = interfaceType;
        this.serviceFactory = serviceFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            String identity = LocalCache.getIdentity().getLevel3();
            Object actualInstance = serviceFactory.getExtensionActualInstance();
            if (actualInstance == null) {
                return null;
            }
            return method.invoke(actualInstance, args);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
}
