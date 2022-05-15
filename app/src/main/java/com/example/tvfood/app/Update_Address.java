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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_Address extends AppCompatActivity {

    private EditText edtdcSN, edtdcPQ;
    private Button btnBack, btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_address);
        btnBack = findViewById(R.id.btnBack_update_address);
        btnSave = findViewById(R.id.btnSave_update_address);
        edtdcSN = findViewById(R.id.edt_dc_SN_update);
        edtdcPQ = findViewById(R.id.edt_dc_PQ_update);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClassBack();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        getUser_Address();


    }

    private void getUser_Address() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + id_user);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    edtdcSN.setText(user.getDiaChiSN());
                    edtdcPQ.setText(user.getDiaChiPQ());
                } else
                    return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void update() {
        String dc = edtdcSN.getText().toString();
        String dc2 = edtdcPQ.getText().toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + id_user);
        myRef.child("diaChiSN").setValue(dc);
        myRef.child("diaChiPQ").setValue(dc2);
        Toast.makeText(this, "Thanh Cong!!", Toast.LENGTH_SHORT).show();
    }

    void checkClassBack() {
        Intent intent1 = getIntent();
        String food_list = intent1.getStringExtra("1");
        if (food_list != null) {
            Intent intent = new Intent(Update_Address.this, Food_List.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(Update_Address.this, Payment.class);
            startActivity(intent);
        }
    }

}
