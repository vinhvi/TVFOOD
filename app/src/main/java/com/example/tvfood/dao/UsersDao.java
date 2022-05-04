package com.example.tvfood.dao;

import com.example.tvfood.entyti.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsersDao {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dataUser = database.getReference("users");

    public void createAccount(Users users) {
        String hoTen = null;
        String email = null;
        String sdt = null;
        String diaChiSN = null;
        String diaChiPQ = null;
        String pass = null;
        users = new Users(hoTen, email, sdt, diaChiSN, diaChiPQ, pass);
        dataUser.setValue(users);

    }

}
