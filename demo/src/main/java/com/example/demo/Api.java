package com.example.demo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author YETLAND
 * @date 2018/9/30 9:30
 */
public interface Api {
    @GET("music/")
    Call<String> load();

    @GET("login")
    Call<String> login(@Query("username") String username, @Query("password") String password);
}
