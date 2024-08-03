package com.example.thrifteeapp.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thrifteeapp.R;
import com.example.thrifteeapp.activities.DetailsActivity;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<Integer> imageIDs;
    private final Clothing clickedItem; // Store the Clothing object associated with the adapter instance
    private final Context context;


    public ImageAdapter(List<Integer> imageIDs, Clothing clickedItem, Context context) {
        this.imageIDs = imageIDs;
        this.clickedItem = clickedItem;
        this.context = context;
    }


    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new ImageAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        int imageId = imageIDs.get(position);
        ((ImageAdapter.ImageViewHolder) holder).imageView.setImageResource(imageId);
    }



    @Override
    public int getItemCount() {
        return imageIDs.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_for_slides);

        }
    }
}