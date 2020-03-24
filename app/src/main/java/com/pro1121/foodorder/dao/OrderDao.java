package com.pro1121.foodorder.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}
