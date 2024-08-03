package com.example.thrifteeapp.helpers;

import java.util.ArrayList;
import java.util.List;

import com.example.thrifteeapp.model.Bottoms;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.Footwear;
import com.example.thrifteeapp.model.Tops;

public class ClothingFilter {

  List<Clothing> filteredClothes;
  List<Clothing> totalClothes;


  private List<Clothing> filterTops(List<Clothing> clothes) {
    List<Clothing> tops = new ArrayList<>();
    for (Clothing c : clothes) {
      if (c instanceof Tops) {
        tops.add(c);
      }
    }
    return tops;
  }
  private List<Clothing> filterBottoms(List<Clothing> clothes) {
    List<Clothing> bottoms = new ArrayList<>();
    for (Clothing c : clothes) {
      if (c instanceof Bottoms) {
        bottoms.add(c);
      }
    }
    return bottoms;
  }
  private List<Clothing> filterFootwear(List<Clothing> clothes) {
    List<Clothing> footwear = new ArrayList<>();
    for (Clothing c : clothes) {
      if (c instanceof Footwear) {
        footwear.add(c);
      }
    }
    return footwear;
  }

  private List<Clothing> filterByGender(List<Clothing> clothes, String gender) {
    List<Clothing> filteredByGender = new ArrayList<>();
    for (Clothing clothing : clothes) {
      if (clothing.getGender().equals(gender)) {
        filteredByGender.add(clothing);
      }
    }
    return filteredByGender;
  }

  private List<Clothing> filterBySubCategory(List<Clothing> clothes, String subcategory) {
    List<Clothing> filteredBySubcategory = new ArrayList<>();
    for (Clothing clothing : clothes) {
      if (clothing.getSubCategory().equals(subcategory)) {
        filteredBySubcategory.add(clothing);
      }
    }
    return filteredBySubcategory;
  }
  public List<Clothing> getFilteredClothes(List<Clothing> clothes, String category) {

    switch (category) {
      case "Tops":
        return filterTops(clothes);
      case "Bottoms":
        return filterBottoms(clothes);
      case "Footwear":
        return filterFootwear(clothes);
      case "Mens":
        return filterByGender(clothes, "Male");
      case "Womens":
        return filterByGender(clothes, "Female");
    }
    return filterBySubCategory(clothes,category);
  }

}
