package com.andersen.sadwyn.remoteimagesviewer;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.andersen.sadwyn.remoteimagesviewer.image.Image;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Sadwyn on 04.04.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Image> images;
    private Context context;
    private GridLayoutManager layoutManager;

    public RecyclerViewAdapter(Context context, ArrayList<Image> images, GridLayoutManager layoutManager) {
        this.context = context;
        this.images = images;
        this.layoutManager = layoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.half_image, parent, false);
        viewHolder = new ImageHolder(v);
        return viewHolder;
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        ImageView leftImage;
        public ImageHolder(View itemView) {
            super(itemView);
            leftImage = (ImageView) itemView.findViewById(R.id.leftImage);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Glide.with(context).load(images.get(position)
                    .getSource().getUrl()).placeholder(context.getResources().getDrawable(android.R.drawable.progress_horizontal)).
                    centerCrop().into(((ImageHolder) holder).leftImage);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 || position % 3 == 0) ? 2 : 1;
    }
}
