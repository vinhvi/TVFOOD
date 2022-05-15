package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Order;
import com.example.tvfood.entyti.Ram;
import com.example.tvfood.entyti.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Complete extends AppCompatActivity {

    private Button btn_OK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoan_thanh);
        btn_OK = findViewById(R.id.btn_OK);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoanThanh();
            }
        });

    }

    void hoanThanh() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String id_user = mAuth.getUid();
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("ram/" + id_user);
        mRef1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    String id_Order = UUID.randomUUID().toString();
                    Ram ram = new Ram(new Order(id_Order), new User(id_user));
                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                    DatabaseReference mRef1 = database1.getReference("ram");
                    mRef1.child(id_user).setValue(ram).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                removeBill(id_Order);
                            }
                        }
                    });
                }
            }
        });
    }

    void removeBill(String id_bill) {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("bill/" + id_bill);
        mRef1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    removeOrderDetail(id_bill);
                }
            }
        });
    }

    void removeOrderDetail(String id_bill) {
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference mRef1 = database1.getReference("orderDetail/" + id_bill);
        mRef1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Complete.this, Food_List.class);
                    intent.putExtra("key", "0");
                    startActivity(intent);
                }
            }
        });
    }
}
