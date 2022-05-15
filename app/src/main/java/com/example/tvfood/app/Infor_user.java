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
import com.example.tvfood.entyti.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Infor_user extends AppCompatActivity {
    private EditText edtName, edtEmail, edtSDT, edtdcSN, edtPQ;
    private Button btnUpdate, btnBack;

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Infor_user.this, Menu.class);
                Infor_user.this.startActivity(intent);
            }
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        getUser();
    }

    private void getUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + id_user);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    edtName.setText(user.getHoTen());
                    edtEmail.setText(user.getEmail());
                    edtSDT.setText(user.getSdt());
                    edtdcSN.setText(user.getDiaChiSN());
                    edtPQ.setText(user.getDiaChiPQ());
                } else
                    return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Infor_user.this, "That Bai!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void update() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        String name = edtName.getText().toString();
        String sdt = edtSDT.getText().toString();
        String email = edtEmail.getText().toString();
        String dc = edtdcSN.getText().toString();
        String dc2 = edtPQ.getText().toString();
        User user = new User(name, email, sdt, dc, dc2);
        myRef.child(id_user).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}
