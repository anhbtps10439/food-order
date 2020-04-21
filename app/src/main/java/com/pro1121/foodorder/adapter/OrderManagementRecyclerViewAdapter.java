package com.pro1121.foodorder.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.OrderModel;

import java.util.ArrayList;

public class OrderManagementRecyclerViewAdapter extends RecyclerView.Adapter<OrderManagementRecyclerViewAdapter.ViewHolder>{


    private ArrayList<OrderModel> dataList;
    private ArrayList<Integer> priceList;
    private Context context;

    public OrderManagementRecyclerViewAdapter(Context context, ArrayList<OrderModel> dataList, ArrayList<Integer> priceList) {
        this.priceList = priceList;
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_order_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvOrderDate.setText(dataList.get(position).getOrderDate());
        holder.tvPrice.setText(priceList.get(position)+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderDate;
        private TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}
