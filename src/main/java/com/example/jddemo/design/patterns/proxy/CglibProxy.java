package com.example.jddemo.design.patterns.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态代理   代理类无需实现接口
 */
public class CglibProxy implements MethodInterceptor {

    public static <T> T getInstance(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CglibProxy());
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("Before:" + method);
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("After:" + method);
        return object;
    }
}
