package com.example.cropprice.Modals;

public class SingleCropModel {
    String bidderName, bidderPrice, bidderImage, bidderTime;

    public SingleCropModel(String bidderName, String bidderPrice, String bidderImage, String bidderTime) {
        this.bidderName = bidderName;
        this.bidderPrice = bidderPrice;
        this.bidderImage = bidderImage;
        this.bidderTime = bidderTime;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public String getBidderPrice() {
        return bidderPrice;
    }

    public void setBidderPrice(String bidderPrice) {
        this.bidderPrice = bidderPrice;
    }

    public String getBidderImage() {
        return bidderImage;
    }

    public void setBidderImage(String bidderImage) {
        this.bidderImage = bidderImage;
    }

    public String getBidderTime() {
        return bidderTime;
    }

    public void setBidderTime(String bidderTime) {
        this.bidderTime = bidderTime;
    }
}
