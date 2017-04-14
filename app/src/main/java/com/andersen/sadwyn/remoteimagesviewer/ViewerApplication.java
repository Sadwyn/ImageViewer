package com.andersen.sadwyn.remoteimagesviewer;

import android.app.Application;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.leakcanary.LeakCanary;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ViewerApplication extends Application {
    private static Api api;
    private Retrofit retrofit;

    public static Api getApi() {
        return api;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }
}
