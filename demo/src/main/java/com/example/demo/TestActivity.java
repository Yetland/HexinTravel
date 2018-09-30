package com.example.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author YETLAND
 * @date 2018/9/27 12:11
 */
public class TestActivity extends AppCompatActivity {

    private Activity mActivity;
    @Inject
    User mUser;

    RetrofitUtil mRetrofitUtil = new RetrofitUtil();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        DaggerUserComponent.builder()
                .userModule(new UserModule())
                .build()
                .inject(this);

        TextView textView = (TextView) findViewById(R.id.tvHello);
        textView.setText(mUser.getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mRetrofitUtil.getApi().load().enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        Log.e("onResponse", "onResponse: ");
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        Log.e("onResponse", "onFailure: ");
//
//                    }
//                });
                mRetrofitUtil.getApi().login("yetland", "123").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(mActivity, "onResponse", Toast.LENGTH_SHORT).show();
                        Log.e("onResponse", "onResponse: " + response.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(mActivity, "onFailure", Toast.LENGTH_SHORT).show();
                        Log.e("onResponse", "onFailure: ");

                    }
                });
            }
        });
    }
}
