package com.example.tvfood.entyti;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String name;
    private String review;
    private double price;
    private String image;

    public Food() {
    }

    public Food(String id) {
        this.id = id;
    }

    public Food(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Food(String id, String name, String review, double price, String image) {
        this.id = id;
        this.name = name;
        this.review = review;
        this.price = price;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", review='" + review + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
