package com.example.tvfood.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tvfood.R;
import com.example.tvfood.entyti.Foods;

import java.util.ArrayList;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHoler> {
    private ArrayList<Foods> mList;
    private Context ctx;


    public FoodAdapter(ArrayList<Foods> mList, Context ctx) {
        this.mList = mList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public FoodViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item, parent, false);
        return new FoodViewHoler(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHoler holder, int position) {
        Foods foods = mList.get(position);
        if (foods == null) {
            return;
        }
        holder.txtName.setText(foods.getName());
        String price = String.valueOf(foods.getPrice());
        holder.txtPrice.setText(price);
        Glide.with(ctx).load(mList.get(position).getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }


    public class FoodViewHoler extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imageView;
        FoodAdapter foodAdapter;


        public FoodViewHoler(@NonNull View itemView, FoodAdapter foodAdapter) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_Name_food);
            txtPrice = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.image_fodd_list);
            this.foodAdapter = foodAdapter;


        }
    }

}
