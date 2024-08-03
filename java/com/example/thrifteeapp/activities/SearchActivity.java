package com.example.thrifteeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thrifteeapp.R;
import com.example.thrifteeapp.helpers.DataProvider;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.ClothingAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private class ViewHolder{
        Button mensButton,womensButton,jacketsButton,
                hoodiesButton,tshirtsButton,jeansButton,shortsButton,skirtsButton,sneakersButton,bootsButton;
        ImageButton backButton;
        GridView itemGrid;
        TextView emptySearchTextView;
        SearchView searchView;
        BottomNavigationView bottomNavigationView;

        public ViewHolder(){
            mensButton = (Button) findViewById(R.id.mens);
            womensButton = (Button) findViewById(R.id.womens);
            jacketsButton = (Button) findViewById(R.id.jackets);
            hoodiesButton = (Button) findViewById(R.id.hoodies);
            tshirtsButton = (Button) findViewById(R.id.tshirt);
            jeansButton = (Button) findViewById(R.id.jeans);
            shortsButton = (Button) findViewById(R.id.shorts);
            skirtsButton = (Button) findViewById(R.id.skirts);
            sneakersButton = (Button) findViewById(R.id.sneakers);
            bootsButton = (Button) findViewById(R.id.boots);
            emptySearchTextView = (TextView) findViewById(R.id.emptysearch);
            backButton = (ImageButton) findViewById(R.id.back_button);
            searchView = (SearchView) findViewById(R.id.search_bar);
            itemGrid = (GridView)findViewById(R.id.gridview_clothing);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        }
    }

    private int lightGray,darkGray,black,white;
    ViewHolder vh;
    DataProvider dp;
    Intent intent;
    List<Clothing> totalClothingList;
    ArrayList<Clothing> filteredClothing,filteredClothingCopy,filteredSearchClothing,listToSearch;
    ClothingAdaptor clothingAdaptor;
    List<Clothing> tempClothing;
    ;

    private String selectedFilter = "all";
    private String currentSearch;
    private ArrayList<String> currentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        initSearchActivity();
        initColours();
        unSelectAllSortButtons();
        initSearchWidgets();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.headerbarcolor));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.headerbarcolor));
        }

        vh.backButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            intent.putExtra("totalClothingItems", (Serializable) dp.getAllClothingItems()); // Assuming clothingList is a Serializable list
            setResult(Activity.RESULT_OK, intent); // Set result code and data for calling activity
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    });
    getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            intent.putExtra("totalClothingItems", (Serializable) dp.getAllClothingItems()); // Assuming clothingList is a Serializable list
            setResult(Activity.RESULT_OK, intent); // Set result code and data for calling activity
            finish(); // Finish current activity (details activity)
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
            return true;
        });
    }

    private void initSearchActivity(){
        vh = new ViewHolder();
        dp = new DataProvider();
        intent= getIntent();
        currentFilter = new ArrayList<String>();
        filteredClothing = new ArrayList<Clothing>();
        filteredSearchClothing = new ArrayList<Clothing>();
        totalClothingList = (List<Clothing>) intent.getSerializableExtra("totalClothingItems");
        dp.setClothingItems(totalClothingList);
    }

    private void initColours(){
        darkGray= ContextCompat.getColor(getApplicationContext(),R.color.darkGray);
        lightGray = ContextCompat.getColor(getApplicationContext(),R.color.lightGray);
        white = ContextCompat.getColor(getApplicationContext(),R.color.white);
        black = ContextCompat.getColor(getApplicationContext(),R.color.black);
    }
    private void initSearchWidgets(){
        vh.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearch = newText;
                List<Clothing> searchClothingList = new ArrayList<Clothing>();
                searchClothingList = filteredClothing.isEmpty() ? totalClothingList : filteredClothing;
                filteredSearchClothing = new ArrayList<Clothing>();
                for (Clothing clothing:searchClothingList){
                    if (clothing.getName().toLowerCase().contains(newText.toLowerCase())||clothing.getSubCategory().toLowerCase().contains(newText.toLowerCase())){
                        filteredSearchClothing.add(clothing);
                    }
                }
                if (filteredSearchClothing.isEmpty()){
                    vh.emptySearchTextView.setVisibility(View.VISIBLE);
                }else {
                    vh.emptySearchTextView.setVisibility((View.INVISIBLE));
                }
                clothingAdaptor = new ClothingAdaptor(getBaseContext(),R.layout.grid_item,filteredSearchClothing,totalClothingList,SearchActivity.this);
                vh.itemGrid.setAdapter(clothingAdaptor);
                return false;
            }
        });
    }

    private void unSelectAllSortButtons(){
        lookUnselected(vh.mensButton);
        lookUnselected(vh.womensButton);
        lookUnselected(vh.jacketsButton);
        lookUnselected(vh.hoodiesButton);
        lookUnselected(vh.tshirtsButton);
        lookUnselected(vh.jeansButton);
        lookUnselected(vh.shortsButton);
        lookUnselected(vh.skirtsButton);
        lookUnselected(vh.sneakersButton);
        lookUnselected(vh.bootsButton);
    }
    private void lookSelected(Button parsedButton){
        parsedButton.setTextColor(white);
        parsedButton.setBackgroundColor(darkGray);
    }
    private void lookUnselected(Button parsedButton){
        parsedButton.setTextColor(black);
        parsedButton.setBackgroundColor(lightGray);
    }

    private void filterList(){
        filteredClothing = new ArrayList<>();
        filteredClothingCopy = new ArrayList<>();
        tempClothing =  new ArrayList<>();
        listToSearch = new ArrayList<>();
        for (String filter : currentFilter) {

            if (filteredSearchClothing.isEmpty()){
                listToSearch = (ArrayList<Clothing>) totalClothingList;
            }else {
                listToSearch = filteredSearchClothing;
            }
            List<Clothing> tempList = new ArrayList<>();

            for (Clothing clothing : listToSearch) {
                if (clothing.getGender().equals(filter)) {
                    tempList.add(clothing);
                } else if (clothing.getSubCategory().equals(filter)) {
                    tempList.add(clothing);
                }
            }
            if (filter.equals("Female")||filter.equals("Male")){
                addFilteredClothesWithNoDuplicates(tempClothing,tempList);

            } else{
                addFilteredClothesWithNoDuplicates(filteredClothing,tempList);
            }


        }
        if(tempClothing.isEmpty()){
            tempClothing=totalClothingList;
        }
        if (filteredClothing.isEmpty()){
            addFilteredClothesWithNoDuplicates(filteredClothing,tempClothing);
        }else {
            filteredClothing.retainAll(tempClothing);
        }

        setClothingAdaptor();
    }

    private void setClothingAdaptor(){
        if (filteredClothing.isEmpty()){
            vh.emptySearchTextView.setVisibility(View.VISIBLE);
        }else {
            vh.emptySearchTextView.setVisibility((View.INVISIBLE));
        }
        clothingAdaptor = new ClothingAdaptor(getBaseContext(),R.layout.grid_item,filteredClothing,totalClothingList,SearchActivity.this);
        vh.itemGrid.setAdapter(clothingAdaptor);
    }
    private List<Clothing> addFilteredClothesWithNoDuplicates(List<Clothing> originalList, List<Clothing> newFilteredClothes) {
        for (Clothing clothing : newFilteredClothes) {
            if (!originalList.contains(clothing)) {
                originalList.add(clothing);
            }
        }
        return originalList;
    }

    public void mensFilterTapped(View view){
        if (!currentFilter.contains("Male")|| currentFilter.isEmpty()){
            currentFilter.add("Male");
            filterList();
            lookSelected(vh.mensButton);

        }
        else {
            currentFilter.remove("Male");
            filterList();
            lookUnselected(vh.mensButton);

        }

    }
    public void womensFilterTapped(View view) {
        if (!currentFilter.contains("Female")|| currentFilter.isEmpty()) {
            currentFilter.add("Female");
            filterList();
            lookSelected(vh.womensButton);

        } else {
            currentFilter.remove("Female");
            filterList();
            lookUnselected(vh.womensButton);

        }
    }


    public void jacketsFilterTapped(View view) {
        if (!currentFilter.contains("Jackets")|| currentFilter.isEmpty()) {
            currentFilter.add("Jackets");
            filterList();
            lookSelected(vh.jacketsButton);

        } else {
            currentFilter.remove("Jackets");
            filterList();
            lookUnselected(vh.jacketsButton);
        }
    }
    public void hoodiesFilterTapped(View view) {
        if (!currentFilter.contains("Hoodies")|| currentFilter.isEmpty()) {
            currentFilter.add("Hoodies");
            filterList();
            lookSelected(vh.hoodiesButton);
        } else {
            currentFilter.remove("Hoodies");
            filterList();
            lookUnselected(vh.hoodiesButton);

        }
    }

    public void tshirtFilterTapped(View view) {
        if (!currentFilter.contains("T-Shirts")|| currentFilter.isEmpty()) {
            currentFilter.add("T-Shirts");
            filterList();
            lookSelected(vh.tshirtsButton);

        } else {
            currentFilter.remove("T-Shirts");
            filterList();
            lookUnselected(vh.tshirtsButton);

        }
    }

    public void jeansFilterTapped(View view) {
        if (!currentFilter.contains("Jeans")|| currentFilter.isEmpty()) {
            currentFilter.add("Jeans");
            filterList();
            lookSelected(vh.jeansButton);

        } else {
            currentFilter.remove("Jeans");
            filterList();
            lookUnselected(vh.jeansButton);
        }
    }

    public void shortsFilterTapped(View view) {
        if (!currentFilter.contains("Shorts")|| currentFilter.isEmpty()) {
            currentFilter.add("Shorts");
            filterList();
            lookSelected(vh.shortsButton);

        } else {
            currentFilter.remove("Shorts");
            filterList();
            lookUnselected(vh.shortsButton);

        }
    }

    public void skirtsFilterTapped(View view) {
        if (!currentFilter.contains("Skirts")|| currentFilter.isEmpty()) {
            currentFilter.add("Skirts");
            filterList();
            lookSelected(vh.skirtsButton);

        } else {
            currentFilter.remove("Skirts");
            filterList();
            lookUnselected(vh.skirtsButton);

        }
    }

    public void bootsFilterTapped(View view) {
        if (!currentFilter.contains("Boots")|| currentFilter.isEmpty()) {
            currentFilter.add("Boots");
            filterList();
            lookSelected(vh.bootsButton);

        } else {
            currentFilter.remove("Boots");
            filterList();
            lookUnselected(vh.bootsButton);

        }
    }

    public void sneakersFilterTapped(View view) {
        if (!currentFilter.contains("Sneakers")|| currentFilter.isEmpty()) {
            currentFilter.add("Sneakers");
            filterList();
            lookSelected(vh.sneakersButton);

        } else {
            currentFilter.remove("Sneakers");
            filterList();
            lookUnselected(vh.sneakersButton);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        totalClothingList = (List<Clothing>) data.getSerializableExtra("totalClothingItems");
        dp.setClothingItems(totalClothingList);
        filterList();
    }
}