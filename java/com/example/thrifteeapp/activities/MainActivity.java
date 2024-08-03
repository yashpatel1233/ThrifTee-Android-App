package com.example.thrifteeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.thrifteeapp.R;
import com.example.thrifteeapp.helpers.DataProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.thrifteeapp.helpers.FavouritesList;
import com.example.thrifteeapp.helpers.ShoppingCart;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.ClothingAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private class ViewHolder {
        CardView topsCardView,bottomsCardView,footwearCardView;
        ImageButton searchButton;
        GridView topPicksGridView;

        BottomNavigationView bottomNavigationView;

        public ViewHolder() {
            topsCardView = (CardView) findViewById(R.id.cardViewTops);
            bottomsCardView  = (CardView) findViewById(R.id.cardViewBottoms);
            searchButton = (ImageButton) findViewById(R.id.search_button);
            footwearCardView = (CardView) findViewById(R.id.cardViewFootwear);
            topPicksGridView = (GridView) findViewById(R.id.gridview_toppicks);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.home_button);
        }
    }

    ViewHolder vh;
    Intent intent;
    DataProvider dp;
    List<Clothing> topPicks;
    FavouritesList favouritesFilter;
    ShoppingCart shoppingCart;
    List<Clothing> totalClothingItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vh = new ViewHolder();
        dp = new DataProvider();
        favouritesFilter = new FavouritesList();
        shoppingCart = new ShoppingCart();
        totalClothingItems = new ArrayList<Clothing>();
        intent = getIntent();
        if (intent.hasExtra("totalClothingItems")) {
            totalClothingItems = (List<Clothing>) intent.getSerializableExtra("totalClothingItems");
        }
        initMainActivity();
        vh.bottomNavigationView.setSelected(true);
        vh.topsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topsListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> topsList = dp.getFilteredClothes("tops");
                topsListActivity.putExtra("selectedCategory", "Tops");
                topsListActivity.putExtra("totalClothingItems",(Serializable) dp.getAllClothingItems());
                topsListActivity.putExtra("categoryList", (Serializable) topsList); // Pass the list
                startActivityForResult(topsListActivity,1);
                Animation scaleInAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale_up);
                vh.topsCardView.startAnimation(scaleInAnimation);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        vh.bottomsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bottomsListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> bottomsList = dp.getFilteredClothes("bottoms");
                bottomsListActivity.putExtra("selectedCategory", "Bottoms");
                bottomsListActivity.putExtra("totalClothingItems",(Serializable) totalClothingItems);
                bottomsListActivity.putExtra("categoryList", (Serializable) bottomsList); // Pass the list
                startActivityForResult(bottomsListActivity,1);
//                Animation zoomInAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.zoom_in);
//                vh.bottomsCardView.startAnimation(zoomInAnimation);
                Animation scaleInAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale_up);
                vh.bottomsCardView.startAnimation(scaleInAnimation);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        vh.topPicksGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(getBaseContext(), DetailsActivity.class);
                Clothing clickedItem = topPicks.get(position);
                detailsIntent.putExtra("clickedItem", clickedItem);
                detailsIntent.putExtra("totalClothingItems", (Serializable) totalClothingItems);
                startActivityForResult(detailsIntent, 1);
            }
        });

        vh.footwearCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent footwearListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> footwearList = dp.getFilteredClothes("footwear");
                footwearListActivity.putExtra("selectedCategory", "Footwear");
                footwearListActivity.putExtra("totalClothingItems",(Serializable) dp.getAllClothingItems());
                footwearListActivity.putExtra("categoryList", (Serializable) footwearList); // Pass the list
                startActivityForResult(footwearListActivity,1);
                Animation scaleInAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale_up);
                vh.footwearCardView.startAnimation(scaleInAnimation);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        vh.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(getBaseContext(), SearchActivity.class);
                searchActivity.putExtra("totalClothingItems", (Serializable) dp.getAllClothingItems()); // Assuming clothingList is a Serializable list// Set result code and data for calling activity
                startActivityForResult(searchActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
        });

        vh.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_button) {
                return true;
            } else if (itemId == R.id.favourites_button) {
                Intent favouritesListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> favouritesList = favouritesFilter.updateFavouritesList(dp.getAllClothingItems());
                favouritesListActivity.putExtra("selectedCategory", "Favourites");
                favouritesListActivity.putExtra("totalClothingItems",(Serializable) dp.getAllClothingItems());
                favouritesListActivity.putExtra("categoryList", (Serializable) favouritesList); // Pass the list
                startActivityForResult(favouritesListActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.cart_button) {
                Intent cartActivity = new Intent(getBaseContext(), CartActivity.class);
                List<Clothing> cartList = shoppingCart.updateCart(dp.getAllClothingItems());
                cartActivity.putExtra("cartName", "Cart");
                cartActivity.putExtra("totalClothingItems",(Serializable) dp.getAllClothingItems());
                cartActivity.putExtra("cartList", (Serializable) cartList); // Pass the list
                startActivityForResult(cartActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            return true;
        });





    }
    private void initMainActivity(){
        if (!totalClothingItems.isEmpty()){
            dp.setClothingItems(totalClothingItems);
        }
        topPicks = dp.mostViewedItems();
        ClothingAdaptor clothingAdaptor = new ClothingAdaptor(MainActivity.this, R.layout.grid_item, topPicks, dp.getAllClothingItems(),this);
        vh.topPicksGridView.setAdapter(clothingAdaptor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vh.bottomNavigationView.setSelected(true);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("totalClothingItems")) {
                totalClothingItems = (List<Clothing>) data.getSerializableExtra("totalClothingItems");
                initMainActivity();
            }
        }
    }
}