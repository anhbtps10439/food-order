package com.pro1121.foodorder.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pro1121.foodorder.model.DishModel;

public class DishDao {
    private DatabaseReference db;
    private Context context;

    public DishDao(Context context)
    {
        this.context = context;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    public void insert (String categoryId, String name, int price, String des, String image)
    {
        String id = categoryId + db.child("dish").push().getKey();

        DishModel dishModel = new DishModel(id, categoryId, name,price, des, image);
        db.child("dish").child(id).setValue(dishModel);
    }
}
