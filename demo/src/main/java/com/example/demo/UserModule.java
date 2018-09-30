package com.example.demo;

import com.example.demo.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author YETLAND
 * @date 2018/9/27 10:51
 */
@Module
public class UserModule {

    @ActivityScope
    @Provides
    public User provideUser() {
        return new User("Apple", 11);
    }

//    @ActivityScope
//    @Provides
//    RetrofitUtil provideRetrofit(User user) {
//        return new RetrofitUtil(user);
//    }
}
