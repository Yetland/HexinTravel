package com.example.demo;

import android.app.Activity;

import dagger.Component;

/**
 * @author YETLAND
 * @date 2018/9/28 14:50
 */
@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(Activity t);
}
