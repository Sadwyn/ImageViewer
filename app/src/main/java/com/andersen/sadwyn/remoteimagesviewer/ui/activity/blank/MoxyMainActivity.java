package com.andersen.sadwyn.remoteimagesviewer.ui.activity.blank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andersen.sadwyn.remoteimagesviewer.R;
import com.andersen.sadwyn.remoteimagesviewer.RecyclerViewAdapter;
import com.andersen.sadwyn.remoteimagesviewer.image.Image;
import com.andersen.sadwyn.remoteimagesviewer.presentation.presenter.blank.MoxyMainPresenter;
import com.andersen.sadwyn.remoteimagesviewer.presentation.view.blank.MoxyMainView;
import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import io.reactivex.Observable;

public class MoxyMainActivity extends MvpActivity implements MoxyMainView, View.OnClickListener {
    public static final String TAG = "MoxyMainActivity";
    @InjectPresenter
    MoxyMainPresenter mMoxyMainPresenter;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    public static final String NEW = "new";
    public static final String TOP = "top";

    private ArrayList<Image> images = new ArrayList<>();

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MoxyMainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moxy_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMoxyMainPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        mMoxyMainPresenter.getImagesByParameter(v.getId() == R.id.newImages ? NEW : TOP);
    }

    public void initializeRecyclerView(ArrayList<Image> images) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), images, layoutManager);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNext(ArrayList<Image> images) {
        this.images.addAll(images);
        progressBar.setVisibility(View.INVISIBLE);
        initializeRecyclerView(images);
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMoxyMainPresenter.onDestroy();
        mMoxyMainPresenter.detachView(this);
    }

}
