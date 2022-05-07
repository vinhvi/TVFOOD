package com.example.tvfood.entyti;

public class Foods {
    private String name;
    private String review;
    private double price;
    private String image;

    public Foods(String name, String review, double price, String image) {
        this.name = name;
        this.review = review;
        this.price = price;
        this.image = image;
    }

    public Foods() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
