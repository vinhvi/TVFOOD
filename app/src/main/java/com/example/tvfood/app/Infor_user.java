package com.example.tvfood.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Infor_user extends AppCompatActivity {
    private EditText edtName, edtEmail, edtSDT, edtdcSN, edtPQ;
    private Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        edtName = findViewById(R.id.edt_Name);
        edtEmail = findViewById(R.id.edtEmail);
        edtdcSN = findViewById(R.id.edtdcSN);
        edtPQ = findViewById(R.id.edtdcPQ);
        edtSDT = findViewById(R.id.edtSDT);
        Find("087654321");
        btnUpdate = findViewById(R.id.btnUpdate);

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
                        if (user.getSdt().contains(keyword)) {
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
}
