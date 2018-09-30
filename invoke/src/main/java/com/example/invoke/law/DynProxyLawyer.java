package com.example.invoke.law;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author YETLAND
 * @date 2018/9/26 16:42
 */
public class DynProxyLawyer implements InvocationHandler {
    /**
     * 被代理的对象
     */
    private Object target;

    DynProxyLawyer(Object obj) {
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method name is " + method.getName());
        return method.invoke(target, args);
    }
}
