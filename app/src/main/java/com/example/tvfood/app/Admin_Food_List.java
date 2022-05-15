package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_Food_List extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminFoodAdapter adminFoodAdapter;
    private ArrayList<Food> arrayList = new ArrayList<>();
    private Button btnBack, btnAdd;
    private EditText edtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_food_list);
        recyclerView = findViewById(R.id.rcv_AdminListFood);
        adminFoodAdapter = new AdminFoodAdapter(arrayList, this, new AdminFoodAdapter.onclick() {
            @Override
            public void add(Food food) {
                getDataItem(food);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adminFoodAdapter);
        edtSearch = findViewById(R.id.editTextTextPersonName);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    search(editable.toString());
                } else {
                    search("");
                }

            }
        });
        btnAdd = findViewById(R.id.button2);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Food_List.this, Add_food.class);
                startActivity(intent);
            }
        });
        getListFood();
    }

    void getDataItem(Food food) {
        Intent intent = new Intent(this, Update_food.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("food_List_admin", food);
        intent.putExtras(bundle);
        this.startActivity(intent);

    }

    private void getListFood() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("foods");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food foods = dataSnapshot.getValue(Food.class);
                    arrayList.add(foods);
                }
                adminFoodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin_Food_List.this, "get data failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search(String s) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("foods");
        Query query = databaseReference.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    arrayList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        Food food32 = dss.getValue(Food.class);
                        arrayList.add(food32);
                    }
                    FoodAdapter myFoodAdapter = new FoodAdapter(arrayList, getApplicationContext(), new FoodAdapter.onclick() {
                        @Override
                        public void add(Food food) {
                            getDataItem(food);
                        }
                    });
                    recyclerView.setAdapter(myFoodAdapter);
                    myFoodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
