package com.pro1121.foodorder.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pro1121.foodorder.model.DetailOrderModel;
import com.pro1121.foodorder.model.DishModel;

public class DetailOrderDao {

    private Context context;
    private DatabaseReference db;

    public DetailOrderDao(Context context) {
        this.context = context;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    public void insert(String orderID, DishModel dishModel, int amount)
    {
        //tạo id cho detailOrder
        String id = db.child("order").push().getKey();
        DetailOrderModel detailOrderModel = new DetailOrderModel(id, dishModel, amount);

        db.child("order").child(orderID).child("detailOrder").child(id).setValue(detailOrderModel);

    }
}