package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Infor_user extends AppCompatActivity {
    private EditText edtName, edtEmail, edtSDT, edtdcSN, edtPQ;
    private Button btnUpdate, btnXoa, btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        edtName = findViewById(R.id.edtName_update);
        edtEmail = findViewById(R.id.edtEmail_update);
        edtdcSN = findViewById(R.id.edtdcSN_update_user);
        edtPQ = findViewById(R.id.edtdcPQ_update_user);
        edtSDT = findViewById(R.id.edtSDT_update);
        btnBack = findViewById(R.id.btnBack_ifor_user);
       Find("087654321");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Infor_user.this, Admin_menu.class);
                Infor_user.this.startActivity(intent);
            }
        });
        btnXoa = findViewById(R.id.btn_Xoa_user);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoa();
            }
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }
    private void xoa(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child("09876523").child("name").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Infor_user.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Infor_user.this, "That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Find(String keyword) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user != null) {
                        if (user.getSdt().equals(keyword)) {
                            edtName.setText(user.getHoTen());
                            edtEmail.setText(user.getEmail());
                            edtSDT.setText(user.getSdt());
                            edtdcSN.setText(user.getDiaChiSN());
                            edtPQ.setText(user.getDiaChiPQ());
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Infor_user.this, "get data failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        String name = edtName.getText().toString();
        String sdt = edtSDT.getText().toString();
        String email = edtEmail.getText().toString();
        String dc = edtdcSN.getText().toString();
        String dc2 = edtPQ.getText().toString();
        Users user = new Users(name, email, sdt, dc, dc2);
        myRef.child(sdt).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Infor_user.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
                    Find("087654321");
                } else {
                    Toast.makeText(Infor_user.this, "That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
