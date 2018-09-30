package com.example.demo;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * @author YETLAND
 * @date 2018/9/28 14:49
 */
@Module
public class TestModule {

    private Activity mActivity;

    public TestModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity proviceActivity() {
        return mActivity;
    }
}
