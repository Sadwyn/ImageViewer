package com.andersen.sadwyn.remoteimagesviewer;

import com.andersen.sadwyn.remoteimagesviewer.image.Child;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Sadwyn on 03.04.2017.
 */

public interface Api {
    @GET("/r/EarthPorn/{place}/.json?limit=100")
    Call<Child> getData(@Path("place") String place);
}
