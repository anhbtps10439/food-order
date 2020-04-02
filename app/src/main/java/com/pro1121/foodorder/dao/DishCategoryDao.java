package com.pro1121.foodorder.dao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro1121.foodorder.model.DishCategoryModel;
import com.pro1121.foodorder.model.UserModel;

import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;
import static com.pro1121.foodorder.LibraryClass.userModelList;

public class DishCategoryDao {

    private DatabaseReference db;
    private Context context;

    public DishCategoryDao(Context context) {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }

    public void insert(String id, String name, String des, String image)
    {
        DishCategoryModel dishCategoryModel = new DishCategoryModel(id, name, des, image);
        db.child("dishCategory").child(id).setValue(dishCategoryModel);
    }

    public void getAllRuntime()
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dishCategoryModelList.clear();
                //mỗi child trong dataSnapshot
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    //tạo đối tượng User và thêm vào List
                    dishCategoryModelList.add(data.getValue(DishCategoryModel.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("dishCategory").addListenerForSingleValueEvent(valueEventListener);
    }
}
