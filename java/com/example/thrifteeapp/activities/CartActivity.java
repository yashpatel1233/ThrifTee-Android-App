package com.example.thrifteeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thrifteeapp.R;
import com.example.thrifteeapp.helpers.ClothingFilter;
import com.example.thrifteeapp.helpers.DataProvider;
import com.example.thrifteeapp.helpers.FavouritesList;
import com.example.thrifteeapp.model.CartClothingAdapter;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.ClothingAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private class ViewHolder{
        ListView itemList;
        TextView totalPriceTextView,emptyCartTextView;
        RelativeLayout totalPriceLayout;
        CardView purchaseButton;
        ImageButton backButton,searchButton;
        BottomNavigationView bottomNavigationView;
        public ViewHolder(){
            itemList = (ListView) findViewById(R.id.cart_listview);
            emptyCartTextView = (TextView) findViewById(R.id.emptycart);
            totalPriceLayout = (RelativeLayout)findViewById(R.id.relativelayout_price);
            purchaseButton = (CardView) findViewById(R.id.purchase_button);
            backButton = (ImageButton) findViewById(R.id.back_button);
            totalPriceTextView = (TextView) findViewById(R.id.total_price);
            searchButton = (ImageButton) findViewById(R.id.search_button);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.cart_button);
        }

    }
    ViewHolder vh;
    DataProvider dp;
    Intent intent;
    ClothingFilter clothingFilter;
    List<Clothing> cartList;
    List<Clothing> totalClothingList;
    CartClothingAdapter cartClothingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        vh = new ViewHolder();
        dp = new DataProvider();
        clothingFilter = new ClothingFilter();
        intent = getIntent();
        totalClothingList = (List<Clothing>) intent.getSerializableExtra("totalClothingItems");
        initCartActivity();

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
            }
        });

        vh.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_button) {
                Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                mainActivity.putExtra("totalClothingItems",(Serializable) totalClothingList);// Pass the list
                startActivityForResult(mainActivity,1);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else if (itemId == R.id.favourites_button) {
                Intent favouritesListActivity = new Intent(getBaseContext(), ListActivity.class);
                List<Clothing> favouritesList = dp.getFavouritesList();
                favouritesListActivity.putExtra("selectedCategory", "Favourites");
                favouritesListActivity.putExtra("totalClothingItems",(Serializable) totalClothingList);
                favouritesListActivity.putExtra("categoryList", (Serializable) favouritesList); // Pass the list
                startActivityForResult(favouritesListActivity,1);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
    private void setEmptyCartText(){
        if (cartList.isEmpty()){
            vh.emptyCartTextView.setVisibility(View.VISIBLE);
            vh.totalPriceLayout.setVisibility(View.INVISIBLE);
            vh.purchaseButton.setVisibility(View.INVISIBLE);
        } else{
            vh.emptyCartTextView.setVisibility(View.INVISIBLE);
            vh.totalPriceLayout.setVisibility(View.VISIBLE);
            vh.purchaseButton.setVisibility(View.VISIBLE);
        }
    }
    private void initCartActivity(){
        dp.setClothingItems(totalClothingList);
        vh.totalPriceTextView.setText("$"+dp.getTotalPrice());
        cartList = dp.updateCart();
        setEmptyCartText();
        cartClothingAdapter = new CartClothingAdapter(this, R.layout.cart_listview_item,cartList,totalClothingList);
        vh.itemList.setAdapter(cartClothingAdapter);
        vh.bottomNavigationView.setSelectedItemId(R.id.cart_button);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("totalClothingItems")) {
                totalClothingList = (List<Clothing>) data.getSerializableExtra("totalClothingItems");
                initCartActivity();
            }
        }
    }

}