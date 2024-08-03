package com.example.thrifteeapp.helpers;

import java.util.List;

import com.example.thrifteeapp.model.Bottoms;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.Footwear;
import com.example.thrifteeapp.model.Tops;

public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DataProvider();
        List<Clothing> clothingItems;
        List<Clothing> cartItems;
        List<Clothing> favouritesList;
        dataProvider.addClothingItem(new Tops("T-Shirt", 30.00, "Blue and black striped t-shirt", "M", "T-shirt","male",1));
        dataProvider.addClothingItem(new Bottoms("Jeans", 50.00, "Blue denim jeans",32, "Jeans","Female",2));
        dataProvider.addClothingItem(new Footwear("Sneakers", 80.50, "White sneakers",9, "Sneakers","Unisex",3));
        clothingItems = dataProvider.getAllClothingItems();
        clothingItems.get(0).incrementViewCount();
        clothingItems.get(0).incrementViewCount();
        clothingItems.get(0).incrementViewCount();
        clothingItems.get(0).incrementViewCount();
        clothingItems.get(1).incrementViewCount();
        clothingItems.get(1).incrementViewCount();
        clothingItems.get(1).incrementViewCount();
        clothingItems.get(2).incrementViewCount();  
        clothingItems.get(2).incrementViewCount();  
        cartItems = dataProvider.mostViewedItems();
        favouritesList = dataProvider.getFavouritesList();
        System.out.println(dataProvider.getTotalPrice());
    }
}
