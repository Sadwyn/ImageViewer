package com.andersen.sadwyn.remoteimagesviewer;

import com.andersen.sadwyn.remoteimagesviewer.image.Image;

import java.util.ArrayList;

/**
 * Created by Sadwyn on 04.04.2017.
 */

public interface ImagesRequestCallback {
    void initializeRecyclerView(ArrayList<Image> images);
}
