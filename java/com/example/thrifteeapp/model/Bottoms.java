package com.example.thrifteeapp.model;

public class Bottoms extends Clothing{

    private int size;

    public Bottoms(String name, double price, String description,int size, String subCategory, String gender, int imageID) {
        super(name, price, description, subCategory, gender, imageID);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
