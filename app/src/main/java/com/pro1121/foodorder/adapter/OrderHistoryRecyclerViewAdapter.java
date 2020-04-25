package com.pro1121.foodorder.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        holder.tvIdOrder.setText("Mã đơn: "+ dataList.get(position).getId());
        holder.tvOrderDate.setText("Ngày đặt: "+ dataList.get(position).getOrderDate());
        holder.tvPrice.setText("Thành tiền: "+priceList.get(position)+"");
        holder.ivDetailOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_detail_order, null, false));

                final AlertDialog alertDialog = builder.create();
                builder.setPositiveButton("Oke",null);
                alertDialog.show();

                Button btnOke = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                RecyclerView rvDish = alertDialog.findViewById(R.id.rvDetailOrder);
                TextView etDes = alertDialog.findViewById(R.id.etDesDetailOrder);
                TextView tvPrice = alertDialog.findViewById(R.id.tvTotalPrice);


                etDes.setText(dataList.get(position).getDes());
                tvPrice.setText(""+priceList.get(position));

                ArrayList<DetailOrderModel> detailOrder = dataList.get(position).getDetailOrderList();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                rvDish.setLayoutManager(layoutManager);
                DetailOrderRecyclerViewAdapter adapter = new DetailOrderRecyclerViewAdapter(context, detailOrder);
                rvDish.setAdapter(adapter);


                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderDate,tvPrice, tvIdOrder;
        private ImageView ivDetailOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvIdOrder = itemView.findViewById(R.id.tvIdOrder);
            ivDetailOrder = itemView.findViewById(R.id.ivDetailOrderButton);

        }
    }
}
