package com.pro1121.foodorder.dao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro1121.foodorder.model.DishModel;

import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;
import static com.pro1121.foodorder.LibraryClass.dishModelList;

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
       // Thêm 1 tí
        dishModelList.add(dishModel);
    }

    public void getAllRuntime()
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dishModelList.clear();
                //mỗi child trong dataSnapshot
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    //tạo đối tượng User và thêm vào List
                    dishModelList.add(data.getValue(DishModel.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("dish").addListenerForSingleValueEvent(valueEventListener);
    }
}
