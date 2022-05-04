package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.dao.UsersDao;
import com.example.tvfood.entyti.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {


    private Button btnBack;
    private EditText txtTen;
    private EditText txtEmail;
    private EditText txtSDT;
    private EditText txtDc;
    private EditText txtPass1;
    private EditText txtPass2;
    private EditText txtDC2;
    private CheckBox btnCB;
    private Button btnDKTK;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        btnBack = findViewById(R.id.btn_back);
        btnCB = findViewById(R.id.btnCheckBox);
        btnDKTK = findViewById(R.id.btnDKTK);
        txtTen = findViewById(R.id.txtHvT);
        txtEmail = findViewById(R.id.txtEmail);
        txtSDT = findViewById(R.id.txtSDT);
        txtDc = findViewById(R.id.txtDC);
        txtDC2 = findViewById(R.id.txtDC2);
        txtPass1 = findViewById(R.id.txtPass1);
        txtPass2 = findViewById(R.id.txtPass2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        btnDKTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnCB.isChecked()) {
                    String p1 = txtPass1.getText().toString();
                    String p2 = txtPass2.getText().toString();
                    if (p1.equals(p2)) {
                        taoTK();
                    }
                }
            }
        });
    }

    private void taoTK() {
        String name = txtTen.getText().toString();
        String sdt = txtSDT.getText().toString();
        String email = txtEmail.getText().toString();
        String dc = txtDc.getText().toString();
        String dc2 = txtDC2.getText().toString();
        String pass = txtPass1.getText().toString();

        Users users = new Users(name, email, sdt, dc, dc2, pass);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataUser = database.getReference("users");
        dataUser.setValue(users);
    }
}
