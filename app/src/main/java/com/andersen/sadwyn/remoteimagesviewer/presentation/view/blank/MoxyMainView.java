package com.andersen.sadwyn.remoteimagesviewer.presentation.view.blank;

import com.andersen.sadwyn.remoteimagesviewer.image.Image;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

public interface MoxyMainView extends MvpView {
   void onNext(ArrayList<Image> images);
   void onError(Throwable e);
   void onComplete();
}
