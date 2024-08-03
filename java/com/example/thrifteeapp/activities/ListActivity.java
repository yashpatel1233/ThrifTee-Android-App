package com.example.thrifteeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.thrifteeapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.thrifteeapp.helpers.ClothingFilter;
import com.example.thrifteeapp.helpers.DataProvider;
import com.example.thrifteeapp.helpers.FavouritesList;
import com.example.thrifteeapp.helpers.ShoppingCart;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.ClothingAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class ListActivity extends AppCompatActivity {




    private class ViewHolder{
        GridView itemGrid;
        TextView categoryName,emptyFavouritesTextView;
        ImageButton backButton,searchButton;
        BottomNavigationView bottomNavigationView;
        LinearLayout dotIndicator;
        public ViewHolder(){
            itemGrid = (GridView) findViewById(R.id.gridview_clothing);
            emptyFavouritesTextView = (TextView)findViewById(R.id.emptyFavourites);
            categoryName = (TextView) findViewById(R.id.category_title);
            backButton = (ImageButton) findViewById(R.id.back_button);
            searchButton = (ImageButton) findViewById(R.id.search_button);
            dotIndicator = (LinearLayout) findViewById(R.id.layout_dots);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        }

    }
    ViewHolder vh;
    DataProvider dp;
    Intent intent;
    List<Clothing> categoryList;
    List<Clothing> totalClothingList;
    ClothingAdaptor clothingAdaptor;

    String selectedCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        vh = new ViewHolder();
        dp = new DataProvider();
        intent = getIntent();
        totalClothingList = (List<Clothing>) intent.getSerializableExtra("totalClothingItems");
        selectedCategory = intent.getStringExtra("selectedCategory");
        initListActivity();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.headerbarcolor));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.headerbarcolor));
        }

        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("totalClothingItems", (Serializable) totalClothingList); // Assuming clothingList is a Serializable list
                setResult(Activity.RESULT_OK, intent); // Set result code and data for calling activity
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                intent.putExtra("totalClothingItems", (Serializable) totalClothingList); // Assuming clothingList is a Serializable list
                setResult(Activity.RESULT_OK, intent); // Set result code and data for calling activity
                finish(); // Finish current activity (details activity)
            }
        });
        vh.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(getBaseContext(), SearchActivity.class);
                searchActivity.putExtra("totalClothingItems", (Serializable) totalClothingList); // Assuming clothingList is a Serializable list// Set result code and data for calling activity
                startActivityForResult(searchActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        vh.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_button) {
                Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                mainActivity.putExtra("totalClothingItems",(Serializable) totalClothingList);// Pass the list
                startActivityForResult(mainActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.favourites_button) {
                Intent favouritesListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> favouritesList = dp.getFavouritesList();
                favouritesListActivity.putExtra("selectedCategory", "Favourites");
                favouritesListActivity.putExtra("totalClothingItems",(Serializable) totalClothingList);
                favouritesListActivity.putExtra("categoryList", (Serializable) favouritesList); // Pass the list
                startActivityForResult(favouritesListActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (itemId == R.id.cart_button) {
                Intent cartActivity = new Intent(getBaseContext(), CartActivity.class);
                List<Clothing> cartList = dp.updateCart();
                cartActivity.putExtra("cartName", "Cart");
                cartActivity.putExtra("totalClothingItems",(Serializable) totalClothingList);
                cartActivity.putExtra("cartList", (Serializable) cartList); // Pass the list
                startActivityForResult(cartActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            return true;
        });
    }

    private void initCategoryList(){
        if (selectedCategory.equals("Favourites")){
            vh.bottomNavigationView.setSelectedItemId(R.id.favourites_button);
            vh.bottomNavigationView.setSelected(true);
            categoryList = dp.getFavouritesList();
            initFavourites();
        } else{
            vh.bottomNavigationView.setSelected(false);
            categoryList = dp.getFilteredClothes(selectedCategory);
        }
    }
    private void initFavourites(){
        if (dp.getFavouritesList().isEmpty()){
            vh.emptyFavouritesTextView.setVisibility(View.VISIBLE);
        }
        else{
            vh.emptyFavouritesTextView.setVisibility(View.INVISIBLE);
        }
    }
    private void initListActivity(){
        dp.setClothingItems(totalClothingList);
        initCategoryList();
        vh.categoryName.setText(selectedCategory);
        ClothingAdaptor clothingAdaptor = new ClothingAdaptor(this, R.layout.grid_item,
                categoryList,totalClothingList,this);
        vh.itemGrid.setAdapter(clothingAdaptor);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("totalClothingItems")) {
                totalClothingList = (List<Clothing>) data.getSerializableExtra("totalClothingItems");
                initListActivity();
            }
        }
    }
}
