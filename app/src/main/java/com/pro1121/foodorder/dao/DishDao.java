package com.pro1121.foodorder.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro1121.foodorder.model.DishModel;

import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;
import static com.pro1121.foodorder.LibraryClass.dishModelList;
import static com.pro1121.foodorder.LibraryClass.downloadPhoto;

public class DishDao {
    private DatabaseReference db;
    private Context context;

    public DishDao(Context context)
    {
        this.context = context;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    public String insert (String categoryId, String name, int price, String des, String image)
    {
        String id = categoryId + db.child("dish").push().getKey();

        DishModel dishModel = new DishModel(id, categoryId, name,price, des, image);
        db.child("dish").child(id).setValue(dishModel);
       // Thêm 1 tí
        dishModelList.add(dishModel);

        return id;
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

//                if (dishModelList.size() > 0)
//
//                {
//                    for (int i = 0; i < dishModelList.size(); i++)
//                    {
//                         downloadPhoto(dishModelList.get(i).getImage(), context, "dish");
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("dish").addListenerForSingleValueEvent(valueEventListener);
    }

    public void delete(String id)
    {
        db.child("dish").child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAll()
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

        db.child("dish").addValueEventListener(valueEventListener);
    }

    public void update(String id, String categoryId, String name, int price, String des, String image)
    {
        db.child("dish").child(id).setValue(new DishModel(id, categoryId, name, price, des, image)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
