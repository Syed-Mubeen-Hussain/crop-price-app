package com.example.cropprice.Modals;

public class HomeCropModel {
    String image, name, price, description, bids, ending_time, qty;

    public HomeCropModel(String image, String name, String price, String description, String bids) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.bids = bids;
    }

    public HomeCropModel(String image, String name, String price, String description, String bids, String qty, String ending_time) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.bids = bids;
        this.ending_time = ending_time;
        this.qty = qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getEnding_time() {
        return ending_time;
    }

    public void setEnding_time(String ending_time) {
        this.ending_time = ending_time;
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
