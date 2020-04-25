package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.adapter.DishCategoryAdapter;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;


public class HomeAdminFragment extends Fragment implements DishAdapter.OnItemClick{

    Toolbar toolbar;
    private ImageButton btn_dish_likest;
    private RecyclerView ryc_you_can_like;
    private ArrayList<DishModel> dishModels_random;
    private ArrayList<Bitmap> picList_ramdom;
    private DishAdapter dish_adapter;
    private int[] z;
    private int x,y, sizeList;
    private Random random = new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        //bắt buộc phải có để button trên toolbar hoạt động
        setHasOptionsMenu(true);


        ryc_you_can_like = view.findViewById(R.id.ryc_you_can_like);

        //Tạo 1 list món ăn ngẫu nhiên

        //Chia 2 recyclerview rồi setAdapter
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        ryc_you_can_like.setLayoutManager(layoutManager);
        dish_adapter = new DishAdapter(getActivity(), LibraryClass.dishModelList, this);
        ryc_you_can_like.setAdapter(dish_adapter);


        btn_dish_likest = view.findViewById(R.id.btn_dish_likest);
        btn_dish_likest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Updating !!!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarAdminCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        setColorToolbarAndStatusBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_home_admin_case,menu);
    }
    // set color toolbar
    public void setColorToolbarAndStatusBar(Toolbar toolbar){
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
