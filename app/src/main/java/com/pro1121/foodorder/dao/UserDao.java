package com.pro1121.foodorder.dao;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pro1121.foodorder.model.UserModel;

public class UserDao {
    private DatabaseReference db;
    private Context context;

    public UserDao(Context context) {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }

    public void insert(String id, String name, String birthday, String email, String password, String role)
    {
        UserModel userModel = new UserModel(id, name, birthday, email, password, role);
        db.child("user").child(id).setValue(userModel);
    }
}
