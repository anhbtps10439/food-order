package com.pro1121.foodorder.huyTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    static Context context;
    List<DishModel> dishModels;

    Adapter(Context context, List<DishModel> dishModels){
        this.context=context;
        this.dishModels=dishModels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView tv_dishName, tv_dishDes, tv_dishPrice;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.iv_dish_image);
            tv_dishName = (TextView)itemView.findViewById(R.id.tv_dish_name);
            tv_dishDes = itemView.findViewById(R.id.tv_dish_des);
            tv_dishPrice = itemView.findViewById(R.id.tv_dish_prices);
        }
        void bindData() {
            RecyclerView.LayoutParams params =(RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (getAdapterPosition()%2 == 0){
                params.setMargins(50,50,25,0);
            }else {
                params.setMargins(25,50,50,0);

            }
            itemView.setLayoutParams(params);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.cardview_dishes, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_dishName.setText("asdas");
        holder.tv_dishDes.setText("asdasdasdasdasdasdasd");
        holder.tv_dishPrice.setText("10000000 VND");
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }
}
