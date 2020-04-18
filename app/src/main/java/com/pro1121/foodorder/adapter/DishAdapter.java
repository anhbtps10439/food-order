package com.pro1121.foodorder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DishModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private ArrayList<DishModel> dishList;
    private ArrayList<Bitmap> picList;
    private Context context;
    private OnItemClick onItemClick;

    public DishAdapter(Context context, ArrayList<DishModel> dishList, ArrayList<Bitmap> picList, OnItemClick onItemClick){
        this.context=context;
        this.dishList=dishList;
        this.picList = picList;
        this.onItemClick=onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_dishes, parent,false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            holder.dis_image.setImageBitmap(picList.get(position));
        }catch (Exception e){
            holder.dis_image.setBackgroundColor(Color.parseColor("#ff3737"));

        }
        holder.name.setText(dishList.get(position).getName());
        holder.des.setText(dishList.get(position).getDes());
        holder.price.setText(dishList.get(position).getPrice()+"");
        holder.bindData();

    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener, View.OnClickListener{
        private TextView name, price, des, show;
        private ImageView dis_image;
        private OnItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            dis_image = itemView.findViewById(R.id.ivDishAvatarDialog);
            name = itemView.findViewById(R.id.tv_dish_name);
            price = itemView.findViewById(R.id.tv_dish_prices);
            des = itemView.findViewById(R.id.tv_dish_des);
            show = itemView.findViewById(R.id.tv_show);
            this.onItemClick=onItemClick;
            show.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onItemClick.onClick(v, getAdapterPosition());
        }
        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public void bindData() {
            RecyclerView.LayoutParams params =(RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (getAdapterPosition()%2 == 0){
                params.setMargins(50,50,25,0);
            }else {
                params.setMargins(25,50,50,0);

            }
            itemView.setLayoutParams(params);
        }
    }

    public interface OnItemClick{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
