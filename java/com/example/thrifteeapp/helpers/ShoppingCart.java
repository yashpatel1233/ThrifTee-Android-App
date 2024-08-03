package com.example.thrifteeapp.helpers;

import java.util.ArrayList;
import java.util.List;

import com.example.thrifteeapp.model.Clothing;

public class ShoppingCart {

  private List<Clothing> cartItems;
  private double totalPrice;

  public List<Clothing> updateCart(List<Clothing> clothingItems) {
    // Implement logic to add item to cart
    cartItems = new ArrayList<>();
    for (Clothing clothing : clothingItems) {
      if (clothing.isInCart()) {
        // Update cart status
        cartItems.add(clothing);
      }
    }
    return cartItems;
  }

  public String getTotalPrice(List<Clothing> cartItems) {

    totalPrice = 0;

    for (Clothing item : cartItems) {
      totalPrice += item.getPrice();
    }

    return Double.toString(totalPrice);
  }
}
