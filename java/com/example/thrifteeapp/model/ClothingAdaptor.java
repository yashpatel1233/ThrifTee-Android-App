package com.example.thrifteeapp.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thrifteeapp.R;
import com.example.thrifteeapp.activities.DetailsActivity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ClothingAdaptor extends ArrayAdapter<Clothing> {


    private class ViewHolder{
        ViewPager2 clothingImageView;
        LinearLayout dotLayout;
        TextView clothingNameTextView,clothingPriceTextView;
        ImageButton favouritesButton;
        public ViewHolder(View currentGridViewItem){
            clothingImageView = (ViewPager2) currentGridViewItem.findViewById(R.id.image_slider);
            dotLayout = (LinearLayout) currentGridViewItem.findViewById(R.id.layout_dots);
            clothingNameTextView = (TextView) currentGridViewItem.findViewById(R.id.gridname);
            clothingPriceTextView = (TextView) currentGridViewItem.findViewById(R.id.gridcost);
            favouritesButton = (ImageButton) currentGridViewItem.findViewById(R.id.favourites_button);
        }
    }


    Context mContext;
    int nLayout;
    List<Clothing> clothing;
    List<Clothing> clothingList;
    Activity activity;

    public ClothingAdaptor(@NonNull Context context, int resource, @NonNull List<Clothing> objects, List<Clothing> clothingList,Activity activity) {
        super(context, resource, objects);
        mContext = context;
        nLayout = resource;
        clothing = objects;
        this.clothingList = clothingList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentGridViewItem = convertView;

        if (currentGridViewItem == null) { // To recycle the objects
            currentGridViewItem = LayoutInflater.from(getContext()).inflate(nLayout, parent, false);
        }
        ViewHolder vh = new ViewHolder(currentGridViewItem);
        Clothing currentClothing = clothing.get(position);
        vh.clothingNameTextView.setText(currentClothing.getName());
        vh.clothingPriceTextView.setText("$"+Double.toString(currentClothing.getPrice()));


        List<Integer> imageIds = generateImageIds(currentClothing);
        if (currentClothing.isFavourite()){
            vh.favouritesButton.setImageResource(R.drawable.favouritesfilled);
        } else {
            vh.favouritesButton.setImageResource(R.drawable.favouritesbutton);
        }
        // Assuming your ViewPager2 adapter is already set
        vh.clothingImageView.setAdapter(new ImageAdapter(imageIds,currentClothing,clothingList,getContext()));
        createDots(vh, imageIds.size());

        vh.clothingImageView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int newPosition) {
                super.onPageSelected(newPosition);
                int numDots = vh.dotLayout.getChildCount();
                for (int i = 0; i < numDots; i++) {
                    ImageView dot = (ImageView) vh.dotLayout.getChildAt(i);
                    dot.setImageResource(i == newPosition ? R.drawable.dot_selected : R.drawable.dot_unselected);
                }
            }
        });
        vh.favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentClothing.isFavourite()) {
                    currentClothing.unFavourite();
                    vh.favouritesButton.setImageResource(R.drawable.favouritesbutton);
                } else{
                    currentClothing.setFavourite();
                    vh.favouritesButton.setImageResource(R.drawable.favouritesfilled);
                }
                clothingList.set(findItemPosition(currentClothing),currentClothing);
            }
        });
        vh.clothingPriceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetailsIntent(currentClothing);
            }
        });
        vh.clothingNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetailsIntent(currentClothing);
            }
        });
        return currentGridViewItem;

    }
    private void createDots(ViewHolder vh, int numDots) {
        vh.dotLayout.removeAllViews();

        for (int i = 0; i < numDots; i++) {
            ImageView dot = new ImageView(mContext);
            dot.setImageResource(R.drawable.dot_selected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f // Equal weight for each dot
            );
            params.setMarginEnd(10); // Set margin between dots
            vh.dotLayout.addView(dot, params);
        }
    }

    private int findItemPosition(Clothing clickedItem) {
            for (int i = 0; i < clothingList.size(); i++) {
                Clothing itemInList = clothingList.get(i);
                if (itemInList.getImageID() == clickedItem.getImageID()) { // Assuming 'id' is a unique identifier
                    return i;
                }
        }
        return -1; // Return -1 if not found (optional)
    }
    private int getResourceId(String resourceName) {
        return getContext().getResources().getIdentifier(resourceName, "drawable", getContext().getPackageName());
    }
    private List<Integer> generateImageIds(Clothing clothing) {
        String baseImageName = "image_" + clothing.getImageID();
        return Arrays.asList(
                getResourceId(baseImageName + "a"),
                getResourceId( baseImageName+ "b"),
                getResourceId( baseImageName+ "c")
        );
    }
    protected void initDetailsIntent(Clothing currentClothing){
        Clothing clickedItem = currentClothing;
        Intent itemDetails = new Intent(getContext(), DetailsActivity.class);
        itemDetails.putExtra("clickedItem", clickedItem);
        itemDetails.putExtra("totalClothingItems", (Serializable) clothingList);
        ClothingAdaptor.this.activity.startActivityForResult(itemDetails, 1);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private final List<Integer> imageIDs;
        private final Clothing currentClothing;
        private final Context context;
        private List<Clothing> clothingList;

        public ImageAdapter(List<Integer> imageIDs, Clothing currentClothing,List<Clothing> clothingList, Context context) {
            this.imageIDs = imageIDs;
            this.currentClothing = currentClothing; // Receive the clickedItem here
            this.clothingList = clothingList;
            this.context = context;
        }


        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
            return new ImageAdapter.ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
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
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initDetailsIntent(currentClothing);

                    }
                });

            }
        }
    }

}
