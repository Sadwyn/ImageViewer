package com.andersen.sadwyn.remoteimagesviewer;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sadwyn on 03.04.2017.
 */

public class ViewerApplication extends Application {
    private static Api api;
    private Retrofit retrofit;

    public static Api getApi() {
        return api;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        api = retrofit.create(Api.class);
    }
}
