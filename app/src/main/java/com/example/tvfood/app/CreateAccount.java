package com.example.tvfood.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private ProgressBar progressBar;
    private Button btnDKTK;
    private Users users;
    //upload user to firebase
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

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
        progressBar = findViewById(R.id.proBar_createAcc);
        progressBar.setVisibility(View.INVISIBLE);

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
                progressBar.setVisibility(View.VISIBLE);
                btnDKTK.setEnabled(false);
                if (btnCB.isChecked()) {
                    taoTK();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnDKTK.setEnabled(true);
                    Toast.makeText(CreateAccount.this,
                            "Vui lòng đọc và chấp nhận các Điều khoản và Điều kiện để đăng ký tài khoản !!",
                            Toast.LENGTH_SHORT).show();
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
        String pass2 = txtPass2.getText().toString();
        if (name.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtTen.setError("Nhập họ và tên của bạn!!");
        } else if (email.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtEmail.setError("Nhập vào email của bạn!!");
        } else if (sdt.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtSDT.setError("Nhập vào số điện thoại của bạn");
        } else if (dc.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtDc.setError("");
        } else if (dc2.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtDC2.setError("");
        } else if (pass.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtPass1.setError("");
        } else if (pass2.length() == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            txtPass2.setError("");
        } else if (pass.equals(pass2)) {
            if (pass.length() < 8) {
                txtPass1.setError("Mật khẩu phải lớn hơn hoặc bằng 8 ký tự");
                txtPass2.setError("Mật khẩu phải lớn hơn hoặc bằng 8 ký tự");
            } else {
                users = new Users(name, email, sdt, dc, dc2, pass);
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference("users");
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btnDKTK.setEnabled(true);
                                    databaseReference.child(sdt).setValue(users);
                                    Toast.makeText(CreateAccount.this,
                                            "Đăng ký tài khoản thành công. Vui lòng đăng nhập để gọi món !!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                                    CreateAccount.this.startActivity(intent);
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btnDKTK.setEnabled(true);
                                    Toast.makeText(CreateAccount.this,
                                            "Đăng ký thất bại!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        } else {
            progressBar.setVisibility(View.INVISIBLE);
            btnDKTK.setEnabled(true);
            Toast.makeText(CreateAccount.this,
                    "Mật khẩu nhập lại không khớp !!",
                    Toast.LENGTH_SHORT).show();
        }


    }
}
