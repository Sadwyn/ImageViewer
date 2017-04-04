package com.andersen.sadwyn.remoteimagesviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andersen.sadwyn.remoteimagesviewer.image.Child;
import com.andersen.sadwyn.remoteimagesviewer.image.Child_;
import com.andersen.sadwyn.remoteimagesviewer.image.Data__;
import com.andersen.sadwyn.remoteimagesviewer.image.Image;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ImagesRequestCallback, View.OnClickListener{
    public static final String NEW = "new";
    public static final String TOP = "top";
    public static final String IMAGE = "image";
    ImagesRequestCallback callback;
    RecyclerView recyclerView;
    Call<Child> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callback = this;
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        getImagesByParameter(TOP);
    }

    private void getImagesByParameter(String parameter) {
        call = getCall(parameter);
        call.enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                   Child child = response.body();
                List<Data__> data = new ArrayList<>();
                for (Child_ child_ : child.getData().getChildren())
                    data.add(child_.getData());

                ArrayList<Image> images = new ArrayList<>() ;
                for (Data__ dataItem : data) {
                    if(dataItem.getPostHint().equals(IMAGE))
                        images.addAll(dataItem.getPreview().getImages());
                    callback.initializeRecyclerView(images);
                }
            }
            @Override
            public void onFailure(Call<Child> call, Throwable t) {
                Log.i("FAILURE", t.getMessage());
            }
        });
    }

    private Call<Child> getCall(String parameter) {
        return ViewerApplication.getApi().getData(parameter);
    }

    @Override
    public void initializeRecyclerView(ArrayList<Image> images) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), images, layoutManager);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return  adapter.getItemViewType(position);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        call.cancel();
    }

    @Override
    public void onClick(View v) {
        call.cancel();
        getImagesByParameter(v.getId() == R.id.newImages ? NEW : TOP);
    }
}
