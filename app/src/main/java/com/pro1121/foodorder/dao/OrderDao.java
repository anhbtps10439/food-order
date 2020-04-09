package com.pro1121.foodorder.dao;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.model.DetailOrderModel;
import com.pro1121.foodorder.model.DishModel;
import com.pro1121.foodorder.model.OrderModel;

import java.util.ArrayList;

import static com.pro1121.foodorder.LibraryClass.orderModelList;

public class OrderDao {

    private Context context;
    private DatabaseReference db;

    public OrderDao(Context context) {
        this.context = context;
        this.db = FirebaseDatabase.getInstance().getReference();
    }


    //return lại cái id để sau đó thêm đơn hàng chi tiết luôn
    public String insert(String orderDate, String userId, String des)
    {
        String id = db.child("order").push().getKey();
        db.child("order").child(id).child("id").setValue(id);
        db.child("order").child(id).child("orderDate").setValue(orderDate);
        db.child("order").child(id).child("userId").setValue(userId);
        db.child("order").child(id).child("des").setValue(des);

        return id;
    }

    public void getAllRuntime()
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderModelList.clear();


                //mỗi child trong order là một order
                for (DataSnapshot orderChild: dataSnapshot.getChildren())
                {
                    //tao doi tuong model
                    OrderModel orderModel = new OrderModel();
                    //set các thuộc tính
                    orderModel.setId(orderChild.child("id").getValue(String.class));
                    orderModel.setDes(orderChild.child("des").getValue(String.class));
                    orderModel.setUserId(orderChild.child("userId").getValue(String.class));
                    orderModel.setOrderDate(orderChild.child("orderDate").getValue(String.class));

                    //tao doi tuong ArrayList để chứa các detailOrder
                    ArrayList<DetailOrderModel> detailOrderList = new ArrayList<>();

                    //mỗi child trong child detailOrder làm một child

                    for (DataSnapshot detailOrderChild: orderChild.child("detailOrder").getChildren())
                    {
                        //tạo đối tương detailOrder
                        //set các thuộc tính
                        DetailOrderModel detailOrderModel = new DetailOrderModel();
                        detailOrderModel.setId(detailOrderChild.child("id").getValue(String.class));
                        detailOrderModel.setAmount(detailOrderChild.child("amount").getValue(Integer.class));
                        detailOrderModel.setDish(detailOrderChild.child("dish").getValue(DishModel.class));

                        detailOrderList.add(detailOrderModel);
                    }

                    //set detailOrderList cho orderModel
                    orderModel.setDetailOrderList(detailOrderList);
                    //add orderModel vào List
                    orderModelList.add(orderModel);
                }
                Log.d("orderList", orderModelList.size()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("order").addListenerForSingleValueEvent(valueEventListener);
    }

}
