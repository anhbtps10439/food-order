package com.pro1121.foodorder.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DetailOrderModel;
import com.pro1121.foodorder.model.OrderModel;

import java.util.ArrayList;

public class OrderHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.ViewHolder>{


    private ArrayList<OrderModel> dataList;
    private ArrayList<Integer> priceList;
    private Context context;

    public OrderHistoryRecyclerViewAdapter(Context context, ArrayList<OrderModel> dataList, ArrayList<Integer> priceList) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvOrderDate.setText(dataList.get(position).getOrderDate());
        holder.tvPrice.setText(priceList.get(position)+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_detail_order, null, false));

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                RecyclerView rvDish = alertDialog.findViewById(R.id.rvDetailOrder);
                EditText etDes = alertDialog.findViewById(R.id.etDesDetailOrder);
                TextView tvPrice = alertDialog.findViewById(R.id.tvTotalPrice);

                etDes.setText(dataList.get(position).getDes());
                tvPrice.setText(""+priceList.get(position));

                ArrayList<DetailOrderModel> detailOrder = dataList.get(position).getDetailOrderList();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                rvDish.setLayoutManager(layoutManager);
                DetailOrderRecyclerViewAdapter adapter = new DetailOrderRecyclerViewAdapter(context, detailOrder);
                rvDish.setAdapter(adapter);
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
