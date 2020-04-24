package com.pro1121.foodorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DetailOrderModel;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;

public class DetailOrderRecyclerViewAdapter extends RecyclerView.Adapter<DetailOrderRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DetailOrderModel> dataList;

    public DetailOrderRecyclerViewAdapter(Context context, ArrayList<DetailOrderModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_detail_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(dataList.get(position).getDish().getName());
        holder.tvPrice.setText(""+dataList.get(position).getDish().getPrice());
        holder.tvAmount.setText(""+dataList.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvPrice;
        TextView tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvDishNameOrder);
            tvPrice = itemView.findViewById(R.id.tvDishPriceOrder);
            tvAmount = itemView.findViewById(R.id.tvAmountOrder);
        }
    }

}
