package com.pro1121.foodorder.huyTest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;
import java.util.List;

public class ClickKindFood extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DishModel> dishModels = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_click_kind_food);

        recyclerView = findViewById(R.id.rv_dishes);

        dishModels.add(new DishModel("1","1","a",1000000,"a","a"));
        dishModels.add(new DishModel("1","1","a",1000000,"a","a"));
        dishModels.add(new DishModel("1","1","a",1000000,"a","a"));
        dishModels.add(new DishModel("1","1","a",1000000,"a","a"));
        dishModels.add(new DishModel("1","1","a",1000000,"a","a"));

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(this,dishModels);
        recyclerView.setAdapter(adapter);

    }
}
