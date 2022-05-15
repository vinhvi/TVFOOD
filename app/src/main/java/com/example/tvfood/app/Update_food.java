package com.example.tvfood.app;

import android.content.Intent;
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
import com.example.tvfood.entyti.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_food extends AppCompatActivity {
    private Button btnUpdate, btnDelete;
    private EditText edtName, edtPrice, edtReview;
    private ImageView imgae;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_food);
        btnUpdate = findViewById(R.id.btn_update_food_update);
        btnDelete = findViewById(R.id.btn_delete_update_food);
        edtName = findViewById(R.id.edt_name_food_update);
        edtPrice = findViewById(R.id.edt_price_update);
        edtReview = findViewById(R.id.edt_review_update);
        imgae = findViewById(R.id.image_food_update);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Null!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Food food = (Food) bundle.get("food_List_admin");
        edtName.setText(food.getName());
        edtPrice.setText(String.valueOf(food.getPrice()));
        edtReview.setText(food.getReview());
        Glide.with(Update_food.this).load(food.getImage()).into(imgae);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoa(food.getId());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(food.getId());
            }
        });

    }

    private void update(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("foods/" + id);
        String name = edtName.getText().toString();
        double price = Double.parseDouble(edtPrice.getText().toString());
        String review = edtReview.getText().toString();
        myRef.child("name").setValue(name);
        myRef.child("price").setValue(price);
        myRef.child("review").setValue(review);
        Toast.makeText(Update_food.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
    }

    private void xoa(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("foods/" + id);
        myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Update_food.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Update_food.this, Admin_Food_List.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Update_food.this, "That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
