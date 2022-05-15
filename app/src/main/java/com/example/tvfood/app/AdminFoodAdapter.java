package com.example.tvfood.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tvfood.R;
import com.example.tvfood.entyti.Food;

import java.util.ArrayList;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.AdminFoodAdapterViewHolder> {
    private ArrayList<Food> arrayList;
    private Context ctx;
    private onclick abc;

    public AdminFoodAdapter(ArrayList<Food> arrayList, Context ctx, onclick abc) {
        this.arrayList = arrayList;
        this.ctx = ctx;
        this.abc = abc;
    }

    public interface onclick {
        void add(Food food);
    }

    @NonNull
    @Override
    public AdminFoodAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_food, parent, false);
        return new AdminFoodAdapterViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodAdapterViewHolder holder, int position) {
        Food foods = arrayList.get(position);
        if (foods == null) {
            return;
        }
        holder.tvName.setText(foods.getName());
        String price = String.valueOf(foods.getPrice());
        holder.tvPrice.setText(price);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abc.add(foods);
            }
        });
        Glide.with(ctx).load(arrayList.get(position).getImage()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class AdminFoodAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvName, tvPrice;
        private ConstraintLayout constraintLayout;
        AdminFoodAdapter adminFoodAdapter;

        public AdminFoodAdapterViewHolder(@NonNull View itemView, AdminFoodAdapter adminFoodAdapter) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.itemFood);
            imageView = itemView.findViewById(R.id.image_admin_itemFood);
            tvName = itemView.findViewById(R.id.tv_Name_adminFood);
            tvPrice = itemView.findViewById(R.id.tv_Price_adminFood);
            this.adminFoodAdapter = adminFoodAdapter;
        }
    }
}
