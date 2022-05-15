package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment extends AppCompatActivity {

    private Button btnback;
    private TextView tv_dcSN, tv_dcFull;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        btnback = findViewById(R.id.btn_Back_payment);
        tv_dcFull = findViewById(R.id.tv_dcFull_payment);
        tv_dcSN = findViewById(R.id.tv_dcSN_payment);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Food_List.class);
                startActivity(intent);
            }
        });
        getAdress();

    }

    private void getAdress() {
        FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + id.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if (user != null) {
                    tv_dcSN.setText(user.getDiaChiSN());
                    tv_dcFull.setText(user.getDiaChiSN() + "," + user.getDiaChiPQ());
                } else {
                    Toast.makeText(Payment.this, "user is nulling", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Payment.this, "Get data failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
