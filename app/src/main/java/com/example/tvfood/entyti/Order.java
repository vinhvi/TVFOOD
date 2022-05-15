package com.example.tvfood.entyti;

public class Order {
    private String id;
    private User id_user;


    public Order(String id, User id_user) {
        this.id = id;
        this.id_user = id_user;
    }

    public Order(String id) {
        this.id = id;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", id_user=" + id_user +
                '}';
    }
}
