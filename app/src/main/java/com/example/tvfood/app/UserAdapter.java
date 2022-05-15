package com.example.tvfood.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Food;
import com.example.tvfood.entyti.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> arrayList;
    private Context ctx;
    private onclick abc;
    private LayoutInflater layoutInflater;

    public UserAdapter(ArrayList<User> arrayList, Context ctx, onclick abc) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        this.abc = abc;
        layoutInflater = LayoutInflater.from(ctx);

    }

    public interface onclick {
        void add(User user);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = arrayList.get(position);
        if (user == null) {
            return;
        }
        holder.tvName.setText(user.getHoTen());
        holder.tvEmail.setText(user.getEmail());
        holder.tvSDT.setText(user.getSdt());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abc.add(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvEmail, tvSDT;
        ConstraintLayout constraintLayout;
        UserAdapter userAdapter;

        public UserViewHolder(@NonNull View itemView, UserAdapter userAdapter) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textView9);
            tvEmail = itemView.findViewById(R.id.textView11);
            tvSDT = itemView.findViewById(R.id.textView13);
            constraintLayout = itemView.findViewById(R.id.itemUser);
            this.userAdapter = userAdapter;
        }
    }
}
