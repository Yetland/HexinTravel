package com.example.demo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author YETLAND
 * @date 2018/9/30 9:26
 */
public class RetrofitUtil {

    private OkHttpClient mOkHttpClient;
    private HttpLoggingInterceptor mHttpLoggingInterceptor;
    private Retrofit mRetrofit;
    private Api mApi;

    public RetrofitUtil() {
        mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);


        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("x-avoscloud-application-id", "hq5TP1JfC8IalSWXQCYNwVk4-gzGzoHsz")
                        .addHeader("x-avoscloud-application-key", "O51KjzzevmasxS3RRCbTDwq0")
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .addNetworkInterceptor(interceptor)
                .readTimeout(8000, TimeUnit.MILLISECONDS)
                .writeTimeout(8000, TimeUnit.MILLISECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://leancloud.cn:443/1.1/")
                .build();
        mApi = mRetrofit.create(Api.class);
    }

    public Api getApi() {
        return mApi;
    }
}
