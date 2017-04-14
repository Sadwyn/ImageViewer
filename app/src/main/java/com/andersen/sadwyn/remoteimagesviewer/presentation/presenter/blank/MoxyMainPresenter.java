package com.andersen.sadwyn.remoteimagesviewer.presentation.presenter.blank;


import com.andersen.sadwyn.remoteimagesviewer.ViewerApplication;
import com.andersen.sadwyn.remoteimagesviewer.image.Child;
import com.andersen.sadwyn.remoteimagesviewer.image.Child_;
import com.andersen.sadwyn.remoteimagesviewer.image.Data__;
import com.andersen.sadwyn.remoteimagesviewer.image.Image;
import com.andersen.sadwyn.remoteimagesviewer.presentation.view.blank.MoxyMainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.andersen.sadwyn.remoteimagesviewer.ui.activity.blank.MoxyMainActivity.NEW;
import static com.andersen.sadwyn.remoteimagesviewer.ui.activity.blank.MoxyMainActivity.TOP;

@InjectViewState
public class MoxyMainPresenter extends MvpPresenter<MoxyMainView> {
    public static final String IMAGE = "image";

    private ArrayList<Image> images = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    public Observable<Child> getImagesByParameter(String parameter) {
        Observable<Child> observable = getObservable(parameter);
        Disposable disposable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
        disposables.add(disposable);
        return observable;
    }

    private Observable<Child> getObservable(String parameter) {
        return ViewerApplication.getApi().getData(parameter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private DisposableObserver<Child> getObserver(){
        return new DisposableObserver<Child>() {
            @Override
            public void onNext(Child child) {
                images.clear();
                List<Data__> data = new ArrayList<>();
                for (Child_ child_ : child.getData().getChildren())
                    data.add(child_.getData());
                for (Data__ dataItem : data) {
                    if (dataItem.getPostHint().equals(IMAGE))
                        images.addAll(dataItem.getPreview().getImages());
                }
                getViewState().onNext(images);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().onError(e);
            }

            @Override
            public void onComplete() {
                getViewState().onComplete();
            }
        };
    }

    @Override
    public void attachView(MoxyMainView view) {
        super.attachView(view);
        if(images.isEmpty())
            getImagesByParameter(NEW);
    }

}

