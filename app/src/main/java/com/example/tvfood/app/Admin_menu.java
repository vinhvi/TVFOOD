package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;

public class Admin_menu extends AppCompatActivity {
    private Button btnLogout, btnaddFood, btnaddUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);
        btnLogout = findViewById(R.id.btn_logout_admin_menu);
        btnaddFood = findViewById(R.id.btn_addFood);
        btnaddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_menu.this, Admin_Food_List.class);
                startActivity(intent);
            }
        });
        btnaddUser = findViewById(R.id.btn_addUser);
        btnaddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_menu.this, List_User.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_menu.this, Login.class);
                startActivity(intent);
            }
        });

    }
}
