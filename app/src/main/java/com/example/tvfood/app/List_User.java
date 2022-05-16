package com.example.tvfood.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvfood.R;
import com.example.tvfood.entyti.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_User extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> arrayList = new ArrayList<>();
    private Button btnBack;
    private EditText edtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user);
        recyclerView = findViewById(R.id.rcv_ListUser);
        userAdapter = new UserAdapter(arrayList, this, new UserAdapter.onclick() {
            @Override
            public void add(User user) {
                getDataItem(user);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
        edtSearch = findViewById(R.id.edt_1234567);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    search(editable.toString());
                } else {
                    search("");
                }

            }
        });
        btnBack = findViewById(R.id.btnBack_ListUser);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_User.this, Admin_menu.class);
                startActivity(intent);
            }
        });
        getListUser();
    }

    void getDataItem(User user) {
        Intent intent = new Intent(this, UpdateUser.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("List_User", user);
        intent.putExtras(bundle);
        this.startActivity(intent);

    }

    private void getListUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    arrayList.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(List_User.this, "get data failed!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search(String s) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");
        Query query = databaseReference.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    arrayList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        User user = dss.getValue(User.class);
                        arrayList.add(user);
                    }
                    UserAdapter userAdapter = new UserAdapter(arrayList, getApplicationContext(), new UserAdapter.onclick() {
                        @Override
                        public void add(User user) {
                            getDataItem(user);
                        }
                    });
                    recyclerView.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
