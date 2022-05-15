package com.example.tvfood.app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tvfood.R;
import com.example.tvfood.entyti.Foods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_food extends AppCompatActivity {
    private Button btnBack, btnUpdate, btnDelete;
    private EditText edtName, edtPrice, edtReview;
    private ImageView imgae;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_food);
        btnBack = findViewById(R.id.btnBack_update_food);
        btnUpdate = findViewById(R.id.btn_update_food_update);
        btnDelete = findViewById(R.id.btn_delete_update_food);
        edtName = findViewById(R.id.edt_name_food_update);
        edtPrice = findViewById(R.id.edt_price_update);
        edtReview = findViewById(R.id.edt_review_update);
        imgae = findViewById(R.id.image_food_update);
        getListFood();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoa();
            }
        });
    }

    private void xoa() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("foods");
        myRef.child("1").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Update_food.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Update_food.this, "That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void getListFood() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("foods");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Foods foods = dataSnapshot.getValue(Foods.class);
                    if (foods.getImage().equals("https://firebasestorage.googleapis.com/v0/b/tvfood-42fc2.appspot.com/o/images%2F9bf6352d-6a58-4f56-9008-fb3913e578ce.png?alt=media&token=c12320eb-49e6-4f0f-a5c8-5c3082d342ac")) {
                        Glide.with(Update_food.this).load(foods.getImage()).into(imgae);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_food.this, "get data failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
