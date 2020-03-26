package com.pro1121.foodorder.huyTest;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;

public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView iv_dish;
    TextView tv_dishName, tv_dishDes, tv_dishPrice;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_dishName= itemView.findViewById(R.id.tv_dish_name);
        tv_dishDes = itemView.findViewById(R.id.tv_dish_des);
        tv_dishPrice = itemView.findViewById(R.id.tv_dish_prices);
    }
}