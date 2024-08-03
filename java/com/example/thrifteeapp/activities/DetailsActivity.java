package com.example.thrifteeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thrifteeapp.R;
import com.example.thrifteeapp.helpers.DataProvider;
import com.example.thrifteeapp.helpers.FavouritesList;
import com.example.thrifteeapp.helpers.ShoppingCart;
import com.example.thrifteeapp.model.CartClothingAdapter;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.ImageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DetailsActivity extends AppCompatActivity{

    private class ViewHolder{
        TextView clothingTitle, clothingPrice, clothingDescription;
        ViewPager2 clothingImages;
        ImageButton backButton,searchButton;
        ImageButton favouritesButton, cartButton;
        BottomNavigationView bottomNavigationView;




        public ViewHolder() {
            clothingTitle = (TextView) findViewById(R.id.clothing_title);
            clothingPrice = (TextView) findViewById(R.id.clothing_price);
            clothingDescription = (TextView) findViewById(R.id.clothing_description);
            clothingImages= (ViewPager2) findViewById(R.id.clothing_images);
            backButton = (ImageButton) findViewById(R.id.back_button);
            searchButton = (ImageButton) findViewById(R.id.search_button);
            favouritesButton =  (ImageButton) findViewById(R.id.favourite_details_button);
            cartButton = (ImageButton) findViewById(R.id.cart_details_button);
            bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottom_navigation);
        }


    }
    ViewHolder vh;
    DataProvider dp;
    Intent intent;
    private LinearLayout dotLayout;

    protected Clothing clickedItem;
    protected List<Clothing> totalClothingItems;
    int clickedItemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        intent = getIntent();
        dp = new DataProvider();
        vh = new ViewHolder();
        clickedItem = (Clothing) intent.getSerializableExtra("clickedItem");
        totalClothingItems = (List<Clothing>) intent.getSerializableExtra("totalClothingItems");
        initDetailsActivity();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.headerbarcolor));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.headerbarcolor));
        }
        vh.favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clickedItem.isFavourite()){
                    //Change image source
                    clickedItem.setFavourite();
                    totalClothingItems.set(clickedItemPosition,clickedItem);
                }else{
                    clickedItem.unFavourite();
                    totalClothingItems.set(clickedItemPosition,clickedItem);
                }
                setFavouritesImage();
                dp.setClothingItems(totalClothingItems);
            }
        });
        vh.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clickedItem.isInCart()){
                    //Change image source
                    clickedItem.setInCart();
                    totalClothingItems.set(clickedItemPosition,clickedItem);
                }else{
                    clickedItem.removeFromCart();
                    totalClothingItems.set(clickedItemPosition,clickedItem);
                }
                setCartImage();
                dp.setClothingItems(totalClothingItems);
            }
        });


        vh.backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("totalClothingItems", (Serializable) totalClothingItems); // Assuming clothingList is a Serializable list
                setResult(Activity.RESULT_OK, intent); // Set result code and data for calling activity
                finish(); // Finish current activity (details activity)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                intent.putExtra("totalClothingItems", (Serializable) totalClothingItems); // Assuming clothingList is a Serializable list
                setResult(Activity.RESULT_OK, intent); // Set result code and data for calling activity
                finish(); // Finish current activity (details activity)
            }
        });

        vh.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(getBaseContext(), SearchActivity.class);
                searchActivity.putExtra("totalClothingItems", (Serializable) totalClothingItems); // Assuming clothingList is a Serializable list// Set result code and data for calling activity
                startActivityForResult(searchActivity,1);
            }
        });

        vh.clothingImages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int newPosition) {
                super.onPageSelected(newPosition);
                updateDots(newPosition);
            }
        });


        vh.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_button) {
                Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                mainActivity.putExtra("totalClothingItems",(Serializable) totalClothingItems);// Pass the list
                startActivityForResult(mainActivity,1);
            } else if (itemId == R.id.favourites_button) {
                Intent favouritesListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> favouritesList = dp.getFavouritesList();
                favouritesListActivity.putExtra("selectedCategory", "Favourites");
                favouritesListActivity.putExtra("totalClothingItems",(Serializable) totalClothingItems);
                favouritesListActivity.putExtra("categoryList", (Serializable) favouritesList); // Pass the list
                startActivityForResult(favouritesListActivity,1);
            } else if (itemId == R.id.cart_button) {
                Intent cartActivity = new Intent(getBaseContext(), CartActivity.class);
                List<Clothing> cartList = dp.updateCart();
                cartActivity.putExtra("cartName", "Cart");
                cartActivity.putExtra("totalClothingItems",(Serializable) totalClothingItems);
                cartActivity.putExtra("cartList", (Serializable) cartList); // Pass the list
                startActivityForResult(cartActivity,1);
            }
            return true;
        });
    }
    private void initDetailsActivity(){
        vh.clothingTitle.setText(clickedItem.getName());
        vh.clothingPrice.setText("$"+Double.toString(clickedItem.getPrice()));
        vh.clothingDescription.setText(clickedItem.getDescription());
        clickedItemPosition = findItemPosition();
        clickedItem.incrementViewCount();
        dotLayout = (LinearLayout) findViewById(R.id.layout_dots);
        totalClothingItems.set(clickedItemPosition,clickedItem);
        dp.setClothingItems(totalClothingItems);
        setFavouritesImage();
        setCartImage();
        createDots(3);
        List<Integer> imageIds = generateImageIds(clickedItem);
        vh.clothingImages.setAdapter(new ImageAdapter(imageIds, clickedItem, this));
    }
    private void createDots(int numDots) {
        dotLayout.removeAllViews();

        for (int i = 0; i < numDots; i++) {
            ImageView dot = new ImageView(getBaseContext());
            dot.setImageResource(R.drawable.dot_selected); // Set first dot selected
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f // Equal weight for each dot
            );
            params.setMarginEnd(10); // Set margin between dots
            dotLayout.addView(dot, params);
        }
    }
    private void updateDots(int position) {
        int numDots = dotLayout.getChildCount();
        for (int i = 0; i < numDots; i++) {
            ImageView dot = (ImageView) dotLayout.getChildAt(i);
            dot.setImageResource(i == position ? R.drawable.dot_selected : R.drawable.dot_unselected);
        }
    }
    private void setFavouritesImage(){
        if(!clickedItem.isFavourite()){
            vh.favouritesButton.setImageResource(getResourceId("favwhite"));
        }else{
            vh.favouritesButton.setImageResource(getResourceId("favouritesfilled"));
        }
    }
    private void setCartImage(){
        if(!clickedItem.isInCart()){
            vh.cartButton.setImageResource(getResourceId("cartwhite"));
        }else{
            vh.cartButton.setImageResource(getResourceId("in_cart_image"));
        }
    }
    private List<Integer> generateImageIds(Clothing clothing) {
        String baseImageName = "image_" + clothing.getImageID();
        return Arrays.asList(
                getResourceId(baseImageName + "a"),
                getResourceId( baseImageName+ "b"),
                getResourceId( baseImageName+ "c")
        );
    }
    private int getResourceId(String resourceName) {
        return getBaseContext().getResources().getIdentifier(resourceName, "drawable", getBaseContext().getPackageName());
    }
    private int findItemPosition() {
        if (totalClothingItems != null && clickedItem != null) {
            for (int i = 0; i < totalClothingItems.size(); i++) {
                Clothing itemInList = totalClothingItems.get(i);
                if (itemInList.getImageID() == clickedItem.getImageID()) { // Assuming 'id' is a unique identifier
                    return i;
                }
            }
        }
        return -1; // Return -1 if not found (optional)
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("totalClothingItems")) {
                totalClothingItems= (List<Clothing>) data.getSerializableExtra("totalClothingItems");
                dp.setClothingItems(totalClothingItems);
                clickedItem = totalClothingItems.get(clickedItemPosition);
                initDetailsActivity();
            }
        }
    }

}