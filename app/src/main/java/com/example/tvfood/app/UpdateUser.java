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

public class UpdateUser extends AppCompatActivity {
    private EditText edtName, edtEmail, edtSDT, edtdcSN, edtPQ;
    private Button btnUpdate, btnBack, btnXoa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        edtName = findViewById(R.id.edt_Name_updatUser);
        edtEmail = findViewById(R.id.edtEmail_updateUser);
        edtdcSN = findViewById(R.id.edtDcSN_updateUser);
        edtPQ = findViewById(R.id.edtDcPQ_updateUser);
        edtSDT = findViewById(R.id.edtSDT_updateUser);
        btnBack = findViewById(R.id.btnBack_updateUser);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUser.this, Admin_menu.class);
                startActivity(intent);
            }
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Null!!", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = (User) bundle.get("List_User");
        String id = user.getId();
        edtName.setText(user.getHoTen());
        edtEmail.setText(user.getEmail());
        edtdcSN.setText(user.getDiaChiSN());
        edtSDT.setText(user.getSdt());
        edtPQ.setText(user.getDiaChiPQ());
        btnXoa = findViewById(R.id.btnXoa_updateUser);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoa(id);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(id);
            }
        });

    }


    private void update(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        String name = edtName.getText().toString();
        String sdt = edtSDT.getText().toString();
        String email = edtEmail.getText().toString();
        String dc = edtdcSN.getText().toString();
        String dc2 = edtPQ.getText().toString();
        User user = new User(name, email, sdt, dc, dc2);
        myRef.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateUser.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUser.this, "That Bai!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void xoa(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + id);
        myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateUser.this, "Thanh COng !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateUser.this, List_User.class);
                    startActivity(intent);
                }
            }
        });

    }
}
