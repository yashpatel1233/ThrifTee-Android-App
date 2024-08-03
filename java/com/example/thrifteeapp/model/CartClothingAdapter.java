package com.example.thrifteeapp.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.thrifteeapp.activities.CartActivity;
import com.example.thrifteeapp.activities.DetailsActivity;
import com.example.thrifteeapp.helpers.ShoppingCart;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CartClothingAdapter extends ArrayAdapter<Clothing> {


    private class ViewHolder{
        ViewPager2 clothingImageView;
        LinearLayout dotLayout;
        TextView clothingNameTextView,clothingPriceTextView,clothingDescriptionTextView;
        ImageButton removeFromCartButton;

        public ViewHolder(View currentListViewItem){
            clothingImageView = (ViewPager2) currentListViewItem.findViewById(R.id.cart_images);
            dotLayout = (LinearLayout) currentListViewItem.findViewById(R.id.layout_dots_cart);
            clothingNameTextView = (TextView) currentListViewItem.findViewById(R.id.clothing_title_listview);
            clothingPriceTextView = (TextView) currentListViewItem.findViewById(R.id.clothing_price_listview);
            clothingDescriptionTextView = (TextView) currentListViewItem.findViewById(R.id.clothing_description_listview);
            removeFromCartButton = (ImageButton) currentListViewItem.findViewById(R.id.remove_from_cart_button);
        }
    }


    Context mContext;
    int nLayout;
    List<Clothing> clothing;
    List<Clothing> clothingList;
    ShoppingCart shoppingCart;
    Activity activity;
    ViewHolder vh;
    public CartClothingAdapter(@NonNull Context context, int resource, @NonNull List<Clothing> objects,
                               List<Clothing> clothingList) {
        super(context, resource, objects);
        mContext = context;
        nLayout = resource;
        clothing = objects;
        this.clothingList = clothingList;
        shoppingCart = new ShoppingCart();// New member variable to hold the activity reference
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentListViewItem = convertView;

        if (currentListViewItem == null) { // To recycle the objects
            currentListViewItem = LayoutInflater.from(getContext()).inflate(nLayout, parent, false);
        }
        vh = new ViewHolder(currentListViewItem);
        Clothing currentClothing = clothing.get(position);
        vh.clothingNameTextView.setText(currentClothing.getName());
        vh.clothingPriceTextView.setText("$"+Double.toString(currentClothing.getPrice()));
        vh.clothingDescriptionTextView.setText(currentClothing.getDescription());
        List<Integer> imageIds = generateImageIds(currentClothing);
        vh.clothingImageView.setAdapter(new ImageAdapter(imageIds,currentClothing,clothingList,getContext()));


        vh.removeFromCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentClothing.removeFromCart();
                int itemPosition = findItemPosition(clothingList,currentClothing);
                clothingList.set(itemPosition,currentClothing);
                Intent cartActivity = new Intent(getContext(), CartActivity.class);
                cartActivity.putExtra("totalClothingItems", (Serializable) clothingList);
                ((Activity) v.getContext()).startActivityForResult(cartActivity, 1);                }
        });
        return currentListViewItem;
    }

    private int findItemPosition(List<Clothing> clothingList, Clothing clickedItem) {
        for (int i = 0; i < clothingList.size(); i++) {
            Clothing itemInList = clothingList.get(i);
            if (itemInList.getImageID() == clickedItem.getImageID()) {
                return i;
            }
        }
        return 0;
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

    private static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private final List<Integer> imageIDs;
        private final Clothing clickedItem; // Store the Clothing object associated with the adapter instance
        private final Context context;
        private final List<Clothing> clothingList;

        public ImageAdapter(List<Integer> imageIDs, Clothing clickedItem,List<Clothing> clothingList, Context context) {
            this.imageIDs = imageIDs;
            this.clickedItem = clickedItem;
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
                imageView = (ImageView) itemView.findViewById(R.id.image_for_slides);
                itemView.setOnClickListener(v -> {
                    Clothing clickedItem = ImageAdapter.this.clickedItem;
                    List<Clothing> clothingList = ImageAdapter.this.clothingList;
                    Intent itemDetails = new Intent(context, DetailsActivity.class);
                    itemDetails.putExtra("clickedItem", clickedItem);
                    itemDetails.putExtra("totalClothingItems", (Serializable) clothingList);
                    ((Activity) v.getContext()).startActivityForResult(itemDetails, 1);
                });
            }
        }
    }

}
