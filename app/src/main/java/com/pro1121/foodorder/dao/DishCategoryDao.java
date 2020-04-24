package com.pro1121.foodorder.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment;
import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.adapter.DishCategoryAdapter;
import com.pro1121.foodorder.model.DishCategoryModel;
import com.pro1121.foodorder.model.DishModel;
import com.pro1121.foodorder.model.UserModel;

import static com.pro1121.foodorder.LibraryClass.categoryPicList;
import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;
import static com.pro1121.foodorder.LibraryClass.dishModelList;
import static com.pro1121.foodorder.LibraryClass.downloadPhoto;
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
        db.child("dishCategory").child(id).setValue(dishCategoryModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("UploadError", "onFailure: " + e.getMessage());
                Toast.makeText(context, "Có lỗi xảy ra! Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
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
                try{

                }catch (Exception e ){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        db.child("dishCategory").addListenerForSingleValueEvent(valueEventListener);
    }

    public void getAll()
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
                CategoryAdminFragment.dishCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        db.child("dishCategory").addValueEventListener(valueEventListener);
    }

    public void delete(String id)
    {
        db.child("dishCategory").child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update(String id, String name, String des, String image)
    {
        db.child("dishCategory").child(id).setValue(new DishCategoryModel(id, name, des, image)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Xin hãy cập nhật lại danh sách", Toast.LENGTH_SHORT).show();
                getAll();
            }
        });
    }
}
