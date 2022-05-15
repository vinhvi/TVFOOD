package com.example.tvfood.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Order;
import com.example.tvfood.entyti.Ram;
import com.example.tvfood.entyti.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Login extends AppCompatActivity {
    private Button btnCreateAcc;
    private EditText edtEmail;
    private EditText edtPass;
    private Button btnQuen;
    private Button btnLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnCreateAcc = findViewById(R.id.btnTTK);
        btnQuen = findViewById(R.id.btnQuenPass);
        edtEmail = findViewById(R.id.txtEmail_Login);
        edtPass = findViewById(R.id.editTextTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.proBar_Login);
        progressBar.setVisibility(View.INVISIBLE);
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                Login.this.startActivity(intent);
            }
        });
        btnQuen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().length() == 0) {
                    edtEmail.setError("Nhập email của bạn !!");
                } else if (edtPass.getText().length() == 0) {
                    edtPass.setError("Nhap mat khau cua ban !!");
                } else {
                    login();
                }
            }
        });
    }

    private void login() {
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);
                            if (email.equals("admin@gmail.com")) {
                                Intent intent1 = new Intent(Login.this, Admin_menu.class);
                                Login.this.startActivity(intent1);
                            } else {
                                String id_user = mAuth.getUid();
                                String id_bill = UUID.randomUUID().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference mRef = database.getReference("ram");
                                Ram ram = new Ram(new Order(id_bill), new User(id_user));
                                mRef.child(id_user).setValue(ram);
                                Intent intent2 = new Intent(Login.this, Food_List.class);
                                Login.this.startActivity(intent2);
                            }
                            edtEmail.setText("");
                            edtPass.setText("");


                        } else {
                            Toast.makeText(Login.this, "email or password khong dung!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);

                        }
                    }
                });

    }
}