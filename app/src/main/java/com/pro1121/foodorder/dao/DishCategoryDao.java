package com.pro1121.foodorder.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pro1121.foodorder.model.DishCategoryModel;

public class DishCategoryDao {

    private DatabaseReference db;
    private Context context;

    public DishCategoryDao(Context context) {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }

    public void insert(String id, String name, String des)
    {
        DishCategoryModel dishCategoryModel = new DishCategoryModel(id, name, des);
        db.child("dishCategory").child(id).setValue(dishCategoryModel);
    }
}
