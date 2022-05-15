package com.example.tvfood.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Food;
import com.example.tvfood.entyti.Order;
import com.example.tvfood.entyti.OrderDetail;
import com.example.tvfood.entyti.Ram;
import com.example.tvfood.entyti.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Food_infor extends AppCompatActivity {
    private Button btnCong, btnTru, btnBack, btnAdd;
    private TextView tvName, tvPrice, tvSL;
    private ImageView imageFood;
    private MultiAutoCompleteTextView mat_Review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_infor);
        btnAdd = findViewById(R.id.btnAdd_food_infor);
        btnBack = findViewById(R.id.btnBack_fodd_ìnor);
        btnCong = findViewById(R.id.btn_Cong);
        btnTru = findViewById(R.id.btnn_Tru);

        tvName = findViewById(R.id.tv_NameFood_infor);
        tvPrice = findViewById(R.id.tv_PriceFood_infor);
        tvSL = findViewById(R.id.tv_SL);
        mat_Review = findViewById(R.id.mtv_Review_infor);
        imageFood = findViewById(R.id.image_food_infor);

        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = tvSL.getText().toString();
                int soLuong1 = Integer.parseInt(soLuong) - 1;
                if (soLuong1 <= 0) {
                    tvSL.setText("1");
                } else
                    tvSL.setText(String.valueOf(soLuong1));
            }
        });
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuong = tvSL.getText().toString();
                int soLuong2 = Integer.parseInt(soLuong) + 1;
                tvSL.setText(String.valueOf(soLuong2));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food_infor.this, Food_List.class);
                startActivity(intent);
            }
        });
        String id_food = getDataFood();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIDOrder(id_food);
                Toast.makeText(Food_infor.this, "Đã thêm món ăn vào giỏ !!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Food_infor.this, Food_List.class);
                startActivity(intent);
            }
        });

    }

    private String getDataFood() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Null!!", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            Food food = (Food) bundle.get("food_infor");
            tvName.setText(food.getName());
            tvPrice.setText(String.valueOf(food.getPrice()));
            imageFood.setImageURI(Uri.parse(food.getImage()));
            String id = food.getId();
            return id;
        }

    }

    void getIDOrder(String id_food) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    createBill(ram.getId_Order().getId());
                    createOrderDetail(ram.getId_Order().getId(), id_food);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void createBill(String id_bill) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("order");
        Order order = new Order(id_bill, new User(id_user));
        mRef.child(id_bill).setValue(order);
    }

    void createOrderDetail(String id_order, String id_food) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("orderDetail");
        String name = tvName.getText().toString();
        double price = Double.parseDouble(tvPrice.getText().toString());
        int soLuong = Integer.parseInt(tvSL.getText().toString());
        OrderDetail orderDetail = new OrderDetail(soLuong, new Order(id_order), new Food(id_food, name, price));
        mRef.child(id_order).child(id_food).setValue(orderDetail);
    }

}
