package com.example.tvfood.entyti;

public class Ram {
    private Order id_Order;
    private User id_User;

    public Ram(Order id_Order, User id_User) {
        this.id_Order = id_Order;
        this.id_User = id_User;
    }

    public Ram(Order id_Order) {
        this.id_Order = id_Order;
    }

    public Ram() {
    }

    public Order getId_Order() {
        return id_Order;
    }

    public void setId_Order(Order id_Order) {
        this.id_Order = id_Order;
    }

    public User getId_User() {
        return id_User;
    }

    public void setId_User(User id_User) {
        this.id_User = id_User;
    }

    @Override
    public String toString() {
        return "Ram{" +
                "id_Order=" + id_Order +
                ", id_User=" + id_User +
                '}';
    }
}
