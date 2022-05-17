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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvfood.R;
import com.example.tvfood.entyti.OrderDetail;
import com.example.tvfood.entyti.Ram;
import com.example.tvfood.entyti.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
    private RecyclerView rcView;
    private ArrayList<OrderDetail> arrayList = new ArrayList<>();
    PaymentAdapter paymentAdapter;
    private Button btnback, btnOrder, btnUpdate;
    private TextView tv_dcSN, tv_dcFull, tvTienFood, tvTongTien, tvPhiShip;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        rcView = findViewById(R.id.rcv_Payment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Payment.this);
        rcView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Payment.this, DividerItemDecoration.VERTICAL);
        rcView.addItemDecoration(dividerItemDecoration);
        paymentAdapter = new PaymentAdapter(arrayList, new PaymentAdapter.onClick() {
            @Override
            public void remove(OrderDetail orderDetail) {
                removeFood(orderDetail);
            }
        }, this);
        rcView.setAdapter(paymentAdapter);
        btnback = findViewById(R.id.btn_Back_payment);
        tv_dcFull = findViewById(R.id.tv_dcFull_payment);
        tv_dcSN = findViewById(R.id.tv_dcSN_payment);
        tvTienFood = findViewById(R.id.tvTienFood);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvPhiShip = findViewById(R.id.tvPhiShip);
        double aa = 20000;
        tvPhiShip.setText(String.valueOf(aa) + "đ");
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Food_List.class);
                startActivity(intent);
            }
        });
        btnUpdate = findViewById(R.id.btnBack_update_dc_payment);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Update_Address.class);
                startActivity(intent);
            }
        });
        getAdress();
        checkPayMent();
        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Complete.class);
                startActivity(intent);
            }
        });
        getIdOrder();
        checkOrder();
    }

    private void getAdress() {
        FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + id.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
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

    private void checkOrder() {
        FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ram/" + id.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    removeBill(ram.getId_Order().getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void removeBill(String id_bill) {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("orderDetail/" + id_bill);
        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderDetail orderDetail = snapshot.getValue(OrderDetail.class);
                if (orderDetail == null) {
                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                    DatabaseReference mRef2 = database1.getReference("order/" + id_bill);
                    mRef2.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void removeFood(OrderDetail orderDetail) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail");
        int soL1 = orderDetail.getSoLuong();
        if (soL1 >= 1) {
            int soL2 = soL1 - 1;
            myRef.child(orderDetail.getId_Order().getId()).child(orderDetail.getId_Food().getId()).child("soLuong").setValue(soL2);
            if (soL2 < 1) {
                myRef.child(orderDetail.getId_Order().getId()).child(orderDetail.getId_Food().getId()).removeValue();
            }
        }
        setTinhTien();
    }

    void getIdOrder() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    getData(ram.getId_Order().getId());
                }
                setTinhTien();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void getData(String id_bill) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail/" + id_bill);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    arrayList.add(orderDetail);
                }
                paymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Payment.this, "load data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void setTinhTien() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    tinhTien(ram.getId_Order().getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void tinhTien(String id_Bill) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail/" + id_Bill);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double ship = 20000;
                double tienFood = 0;
                double tong = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    String aa = String.valueOf(orderDetail.getSoLuong());
                    tienFood = (Double.parseDouble(aa) * orderDetail.getId_Food().getPrice()) + tienFood;
                    tong = tienFood + ship;
                }
                tvTienFood.setText(String.valueOf(tienFood) + "đ");
                tvTongTien.setText(String.valueOf(tong) + "đ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Payment.this, "load data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void checkPayMent() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference("ram/" + id_user);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ram ram = snapshot.getValue(Ram.class);
                if (ram != null) {
                    setPayUp(ram.getId_Order().getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void setPayUp(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("orderDetail/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() <= 0) {
                    btnOrder.setEnabled(false);
                } else
                    btnOrder.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
