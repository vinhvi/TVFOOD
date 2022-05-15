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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
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
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                MainActivity.this.startActivity(intent);
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
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);
                            if (email.equals("admin@gmail.com")) {
                                Intent intent1 = new Intent(MainActivity.this, Admin_menu.class);
                                MainActivity.this.startActivity(intent1);
                            } else {
                                Intent intent2 = new Intent(MainActivity.this, Food_List.class);
                                intent2.putExtra("key1", email);
                                MainActivity.this.startActivity(intent2);
                            }
                            edtEmail.setText("");
                            edtPass.setText("");


                        } else {
                            Toast.makeText(MainActivity.this, "email or password khong dung!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);

                        }
                    }
                });

    }
}