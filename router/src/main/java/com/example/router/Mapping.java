package com.example.router;

import android.app.Activity;

/**
 * @author YETLAND
 * @date 2018/9/29 16:06
 */
public class Mapping {

    private String mValue;
    private Class<? extends Activity> mActivity;

    public Mapping(String value, Class<? extends Activity> activity) {
        mValue = value;
        mActivity = activity;
    }
}
