package com.example.thrifteeapp.model;

public class Tops extends Clothing{

    private String size;

    public Tops(String name, double price, String description,String size, String subCategory, String gender, int imageID) {
        super(name, price, description, subCategory, gender, imageID);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

}
