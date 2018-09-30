package com.example.invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author YETLAND
 * @date 2018/9/26 16:16
 */
public class ProductHandler implements InvocationHandler {

    private Object object;

    public Object bind(Object object) {
        this.object = object;
        //类加载器
        ClassLoader classLoader = object.getClass().getClassLoader();
        //获取实现的接口
        Class<?>[] interfaces = object.getClass().getInterfaces();
        //创建代理者
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        if (method.getName().equals("buy")) {
            if (objects != null && objects.length > 0) {
                Object object = objects[0];
                if (object instanceof Double) {
                    object = 222.222;
                }
                objects[0] = object;
            }
        }
        if (method.getName().equals("name")) {
            if (objects != null && objects.length > 0) {
                Object object = objects[0];
                if (object instanceof String) {
                    object = "hahaha";
                }
                objects[0] = object;
            }
        }
        return method.invoke(this.object, objects);
    }

    public static ProductHandler newInstance() {
        return new ProductHandler();
    }
}
