package com.andersen.sadwyn.remoteimagesviewer;

import com.andersen.sadwyn.remoteimagesviewer.image.Child;

import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Api {
    @GET("/r/EarthPorn/{place}/.json?limit=100")
    io.reactivex.Observable<Child> getData(@Path("place") String place);
}
