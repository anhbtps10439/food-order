package com.pro1121.foodorder.huyTest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DishModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClickKindFood extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DishModel> dishModels = new ArrayList<>();
    Bitmap bm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_click_kind_food);

        recyclerView = findViewById(R.id.rv_dishes);

        // Chuyển ảnh trong drawable thành dạng chuỗi mã hóa
        String enconded = convertImgToString(this,R.drawable.pizza);

        dishModels.add(new DishModel("1","1","Pizza",1000000,"Một cái phần mô tả dàiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii vcl",enconded));
        dishModels.add(new DishModel("1","1","Pizza",1000000,"Một cái phần mô tả dàiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii vcl",enconded));
        dishModels.add(new DishModel("1","1","Pizza",1000000,"Một cái phần mô tả dàiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii vcl",enconded));
        dishModels.add(new DishModel("1","1","Pizza",1000000,"Một cái phần mô tả dàiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii vcl",enconded));
        dishModels.add(new DishModel("1","1","Pizza",1000000,"Một cái phần mô tả dàiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii vcl",enconded));



        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(this,dishModels);
        recyclerView.setAdapter(adapter);

    }
    public static String convertImgToString(Context context, int id){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encoded = Base64.encodeToString(b,Base64.DEFAULT);
        return encoded;
    }

    public static Bitmap convertStringToImg(String s){
        byte[] b = Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }
}