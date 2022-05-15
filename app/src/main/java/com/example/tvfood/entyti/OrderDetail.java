package com.example.tvfood.entyti;

public class OrderDetail {
    private int soLuong;
    private Order id_Order;
    private Food id_Food;

    public OrderDetail(int soLuong, Order id_Order, Food id_Food) {
        this.soLuong = soLuong;
        this.id_Order = id_Order;
        this.id_Food = id_Food;
    }

    public OrderDetail(Food id_Food) {
        this.id_Food = id_Food;
    }

    public OrderDetail(Order id_Order, Food id_Food) {
        this.id_Order = id_Order;
        this.id_Food = id_Food;
    }

    public OrderDetail() {
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "soLuong=" + soLuong +
                ", id_Order=" + id_Order +
                ", id_Food=" + id_Food +
                '}';
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Order getId_Order() {
        return id_Order;
    }

    public void setId_Order(Order id_Order) {
        this.id_Order = id_Order;
    }

    public Food getId_Food() {
        return id_Food;
    }

    public void setId_Food(Food id_Food) {
        this.id_Food = id_Food;
    }
}
