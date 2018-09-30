package com.example.invoke.law;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author YETLAND
 * @date 2018/9/26 16:40
 */
public class ProxyFactory {

    private static Object getDynProxy(Object target) {
        InvocationHandler handler = new DynProxyLawyer(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }

    public static void main(String[] args) {
        ILawSuit proxy = (ILawSuit) ProxyFactory.getDynProxy(new CuiHuaNiu());
        proxy.submit("submit 111");
        proxy.defend();
    }
}
