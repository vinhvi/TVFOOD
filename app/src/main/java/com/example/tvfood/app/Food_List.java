package com.example.tvfood.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Food;
import com.example.tvfood.entyti.Ram;
import com.example.tvfood.entyti.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Food_List extends AppCompatActivity {
    private RecyclerView rcvFood;
    private FoodAdapter mFoodAdapter;
    private EditText edtFind;
    private Button btnMenu, btnUpdateAddress;
    private ImageButton btn_payment;
    private TextView tv_dc, tv_soluong;
    private Context ctx;
    ArrayList<Food> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        arrayList = new ArrayList<>();
        rcvFood = findViewById(R.id.rcv_list_foods);
        tv_soluong = findViewById(R.id.txt_int);
        mFoodAdapter = new FoodAdapter(arrayList, this, new FoodAdapter.onclick() {
            @Override
            public void add(Food food) {
                getDataItem(food);
            }
        });
        rcvFood.setLayoutManager(new LinearLayoutManager(this));
        rcvFood.setAdapter(mFoodAdapter);
        btnMenu = findViewById(R.id.btnBack_update_dc_payment);
        btnUpdateAddress = findViewById(R.id.btnUpdate_dc_list_food);
        btnUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendupdate();
            }
        });
        tv_dc = findViewById(R.id.tv_dcSN);
        btn_payment = findViewById(R.id.btn_Payment);
        edtFind = findViewById(R.id.edtName_update);
        edtFind.addTextChangedListener(new TextWatcher() {
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
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Food_List.this, Payment.class);
                startActivity(intent);
            }
        });
        getAdress();
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Food_List.this, Menu.class);
                startActivity(intent2);
            }
        });
        getListFood();
        setSL();
        check();
    }

    private void getAdress() {
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + fuser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    tv_dc.setText(user.getDiaChiSN());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Food_List.this, "Get data failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getDataItem(Food food) {
        Intent intent = new Intent(this, Food_infor.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("food_infor", food);
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
                mFoodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Food_List.this, "get data failed!!", Toast.LENGTH_SHORT).show();
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
                    rcvFood.setAdapter(myFoodAdapter);
                    myFoodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void setSL() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ram/" + id_user);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    String id_Bill = ram.getId_Order().getId();
                    getSLuong(id_Bill);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getSLuong(String id_Bill) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail/" + id_Bill);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    return;
                } else {
                    String i = String.valueOf(snapshot.getChildrenCount());
                    tv_soluong.setText(i);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void check() {
        Intent intent = getIntent();
        String aa = intent.getStringExtra("key");
        if (aa == null) {
            return;
        } else
            tv_soluong.setText(aa);
    }

    void sendupdate() {
        Intent intent = new Intent(Food_List.this, Update_Address.class);
        intent.putExtra("1", "Food_List");
        startActivity(intent);
    }

}
