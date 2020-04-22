package com.pro1121.foodorder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DishModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.pro1121.foodorder.LibraryClass.buyAmountList;
import static com.pro1121.foodorder.LibraryClass.buyList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private ArrayList<DishModel> dishList;
    private ArrayList<Bitmap> picList;
    private Context context;
    private OnItemClick onItemClick;

    public DishAdapter(Context context, ArrayList<DishModel> dishList, @Nullable ArrayList<Bitmap> picList, OnItemClick onItemClick){
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //set background id ff3737 if picList
        try{
            holder.dis_image.setImageBitmap(picList.get(position));
        }catch (Exception e){
            holder.dis_image.setBackgroundColor(Color.parseColor("#ff3737"));
        }
        holder.name.setText(dishList.get(position).getName());
        holder.des.setText(dishList.get(position).getDes());
        holder.price.setText(dishList.get(position).getPrice()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_dish_info, null, false));

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final Button btnAddToCart = alertDialog.findViewById(R.id.btnAddToCartDialog);
                Button btnPlus = alertDialog.findViewById(R.id.btnPlus);
                final Button btnSub = alertDialog.findViewById(R.id.btnSub);
                final TextView tvAmount = alertDialog.findViewById(R.id.tvAmoumt);
                TextView tvName = alertDialog.findViewById(R.id.tvDishNameDialog);
                TextView tvPrice = alertDialog.findViewById(R.id.tvDishPriceDialog);
                TextView tvDes = alertDialog.findViewById(R.id.tvDishDesDialog);

                tvName.setText(dishList.get(position).getName());
                tvPrice.setText(dishList.get(position).getPrice() + "");
                tvDes.setText(dishList.get(position).getDes());
                btnAddToCart.setEnabled(false); //ban đầu không cho nút hoạt động

                //cộng trừ số lượng

                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int amount = Integer.parseInt(tvAmount.getText().toString());
                        amount++;
                        tvAmount.setText("" + amount);

                        //bấm nút cộng thì sẽ enable nút trừ và add
                        btnAddToCart.setEnabled(true);
                        btnSub.setEnabled(true);
                    }
                });

                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int amount = Integer.parseInt(tvAmount.getText().toString());
                        amount--;
                        //nếu amount = 0 thì disable nút add và nút trừ
                        if (amount <= 0)
                        {
                            tvAmount.setText("" + 0);
                            btnAddToCart.setEnabled(false);
                            btnSub.setEnabled(false);
                        }
                        else
                        {
                            tvAmount.setText(amount+"");
                        }
                    }
                });

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int amount = Integer.parseInt(tvAmount.getText().toString());
                        //nếu amount = 0 thì không làm gì cả

                        if (amount > 0)
                        {
                            buyList.add(dishList.get(position));
                            buyAmountList.add(amount);
                            alertDialog.dismiss();
                            Log.e("size", "List: " + buyList.size() + "    Amount size: " + buyAmountList.size());
                        }
                    }
                });
            }
        });

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
