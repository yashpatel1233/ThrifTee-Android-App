package com.example.thrifteeapp.helpers;

import java.util.ArrayList;
import java.util.List;

import com.example.thrifteeapp.model.Clothing;

public class FavouritesList {
  private List<Clothing> favouritesList;

  public List<Clothing> updateFavouritesList(List<Clothing> clothingItems) {
    // Implement logic to add item to favourites list
    favouritesList = new ArrayList<>();
    for (Clothing clothing : clothingItems) {
      if (clothing.isFavourite()) {
        // Update favourites list
        favouritesList.add(clothing);
      }
    }
    return favouritesList;
  }
}
