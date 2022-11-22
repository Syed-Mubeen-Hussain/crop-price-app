package com.example.cropprice.Modals;

public class HomeCropModel {
    String image, name, price, description, bids;

    public HomeCropModel(String image, String name, String price, String description, String bids) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.bids = bids;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }
}
