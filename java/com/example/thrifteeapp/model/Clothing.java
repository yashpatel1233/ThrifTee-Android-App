package com.example.thrifteeapp.model;

public abstract class Clothing implements java.io.Serializable{
    private String name;
    private double price;
    private boolean isFavourite;
    private boolean inCart;
    private int totalFavourites;
    private int viewCount;
    private String description;
    private String subCategory;
    private String gender;
    private int imageID;

    public Clothing(String name, double price, String description, String subCategory,String gender, int imageID) {
        this.name = name;
        this.price = price;
        isFavourite = false;
        inCart = false;
        totalFavourites = 0;
        viewCount = 0;
        this.description = description;
        this.subCategory = subCategory;
        this.gender = gender;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite() {
        isFavourite = true;
    }

    public void unFavourite() {
        isFavourite = false;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart() {
        inCart = true;
    }

    public void removeFromCart() {
        inCart = false;
    }

    public int getTotalFavourites() {
        return totalFavourites;
    }
    
    public int getViewCount() {
        return viewCount;
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public String getDescription() {
        return description;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getGender() {
        return gender;
    }
    public int getImageID() {
        return imageID;
    }
}
