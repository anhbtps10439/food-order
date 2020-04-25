package com.pro1121.foodorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;

public class CartDishRecyclerViewAdapter extends RecyclerView.Adapter<CartDishRecyclerViewAdapter.ViewHolder>{

    private ArrayList<DishModel> dataList;
    private ArrayList<Integer> amountList;
    private Context context;

    public CartDishRecyclerViewAdapter(ArrayList<DishModel> dataList, ArrayList<Integer> amountList, Context context) {
        this.dataList = dataList;
        this.amountList = amountList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_dish_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvName.setText(dataList.get(position).getName());
        holder.tvName.setText("Đơn giá: " + dataList.get(position).getPrice() + "đ");
        holder.tvAmount.setText(""+amountList.get(position));
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountList.remove(position);
                dataList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount =  Integer.parseInt(holder.tvAmount.getText().toString());
                amount++;
                holder.tvAmount.setText(""+amount);
                amountList.set(position, amount);

            }
        });
        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount =  Integer.parseInt(holder.tvAmount.getText().toString());
                amount--;

                if (amount <= 0)
                {
                    holder.tvAmount.setText("0");
                    amountList.set(position, amount);
                }
                else
                {
                    holder.tvAmount.setText(""+amount);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAmount;
        TextView tvPrice;
        Button btnSub;
        Button btnPlus;
        ImageView ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvDishNameCart);
            tvAmount = itemView.findViewById(R.id.tvAmountCart);
            tvPrice = itemView.findViewById(R.id.tvPriceCart);
            btnSub = itemView.findViewById(R.id.btnSubCart);
            btnPlus = itemView.findViewById(R.id.btnPlusCart);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }
}
