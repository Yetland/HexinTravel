package com.example.demo;

import com.example.demo.dagger.ActivityScope;

import dagger.Component;

/**
 * @author YETLAND
 * @date 2018/9/27 10:52
 */
@ActivityScope
@Component(modules = UserModule.class)
public interface UserComponent {

    void inject(TestActivity testActivity);
}
