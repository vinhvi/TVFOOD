package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Foods;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Food_List extends AppCompatActivity {
    private RecyclerView rcvFood;
    private FoodAdapter mFoodAdapter;
    private List<Foods> mFoods;
    private Button btnMenu;
    private ImageButton btn_payment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        rcvFood = findViewById(R.id.rcv_list_foods);
        mFoods = new ArrayList<>();
        mFoodAdapter = new FoodAdapter(mFoods, this);
        rcvFood.setLayoutManager(new LinearLayoutManager(this));
        rcvFood.setAdapter(mFoodAdapter);
        btnMenu = findViewById(R.id.btnMenu);
        btn_payment = findViewById(R.id.btn_Payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food_List.this, Payment.class);
                startActivity(intent);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food_List.this, Menu.class);
                startActivity(intent);
            }
        });
        getListFood();
    }

    private void getListFood() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("foods");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Foods foods = dataSnapshot.getValue(Foods.class);
                    mFoods.add(foods);
                }
                mFoodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Food_List.this, "get data failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
