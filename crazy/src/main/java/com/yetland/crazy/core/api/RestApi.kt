package com.yetland.crazy.core.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.yetland.crazy.core.app.HexinApplication
import com.yetland.crazy.core.utils.isNetworkAvailable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @Name:           RestApi
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
class RestApi {

    var appService: AppService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheFile = File(HexinApplication().context?.cacheDir, "cache")
        val cache = Cache(cacheFile, (1024 * 1024 * 100).toLong()) //100Mb

        val mInterceptor = Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                    .addHeader("X-LC-Id", X_LC_Id)
                    .addHeader("X-LC-Key", X_LC_Key)
                    .addHeader("Content-Type", "application/json")
                    .build())
        }
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(8000, TimeUnit.MILLISECONDS)
                .connectTimeout(8000, TimeUnit.MILLISECONDS)
                .addInterceptor(mInterceptor)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(HttpCacheInterceptor())
                .cache(cache)
                .build()


        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create()

        // 返回值一定，可以直接解析为json
        val jsonRetrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        // 返回的类型为String，返回后将数据解析
        val stringRetrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        appService = jsonRetrofit.create(AppService::class.java)
    }

    //在访问HttpMethods时创建单例
    private object SingletonHolder {
        val INSTANCE = RestApi()
    }

    private inner class HttpCacheInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            if (!isNetworkAvailable(HexinApplication().context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                Log.d("OkHttp", "no network")
            }

            val originalResponse = chain.proceed(request)
            if (isNetworkAvailable(HexinApplication().context)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                val cacheControl = request.cacheControl().toString()
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build()
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build()
            }
        }
    }

    companion object {

        private val X_LC_Id = "hq5TP1JfC8IalSWXQCYNwVk4-gzGzoHsz"
        private val X_LC_Key = "O51KjzzevmasxS3RRCbTDwq0"
        private val BASE_URL = "https://leancloud.cn:443/1.1/"

        //获取单例
        val instance: RestApi
            get() = SingletonHolder.INSTANCE
    }
}