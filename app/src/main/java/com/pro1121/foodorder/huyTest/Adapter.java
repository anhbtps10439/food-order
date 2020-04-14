package com.pro1121.foodorder.huyTest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;
import java.util.List;

import static com.pro1121.foodorder.activity.SignInOut.MainActivity.dishs_img;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    static Context context;
    List<DishModel> dishModels;

    public Adapter(Context context, List<DishModel> dishModels){
        this.context=context;
        this.dishModels=dishModels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout imageView;
        public TextView tv_dishName, tv_dishDes, tv_dishPrice;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_dish_image);
            tv_dishName = itemView.findViewById(R.id.tv_dish_name);
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
        DishModel dish = dishModels.get(position);
        holder.imageView.setBackgroundColor(Color.RED);
        holder.tv_dishName.setText(dish.getName());
        holder.tv_dishDes.setText(dish.getDes());
        holder.tv_dishPrice.setText(dish.getPrice()+" VND");
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }
}
