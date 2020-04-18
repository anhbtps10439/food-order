package com.pro1121.foodorder.activity.UserCase.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;
import java.util.List;

public class DishUserFragment extends Fragment implements DishAdapter.OnItemClick{
    private Toolbar toolbar;
    private TextView tv_set_dish_category_name;
    private RecyclerView ryc_dish;
    private FloatingActionButton flb_add;
    private String idCategory, nameCategory;
    private ArrayList<DishModel> dishList;
    private ArrayList<Bitmap> picList;
    private DishAdapter dishAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setColorToolbarAndStatusBar(toolbar);
        View view = inflater.inflate(R.layout.fragment_dish,container,false);

        tv_set_dish_category_name = view.findViewById(R.id.tv_set_dish_category_name);
        ryc_dish = view.findViewById(R.id.ryc_dish);
        flb_add = view.findViewById(R.id.flb_add_dish);
        flb_add.hide();

        dishList = LibraryClass.dishFilter(idCategory);
        picList = LibraryClass.dishPicFilter(idCategory);

        //=============

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        ryc_dish.setLayoutManager(layoutManager);
        dishAdapter = new DishAdapter(getActivity(), dishList, picList, this);
        ryc_dish.setAdapter(dishAdapter);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_user_case, new CategoryUserFragment()).commit();
            }
        });

        idCategory = getArguments().getString("id");
        nameCategory = getArguments().getString("nameCategory");
        Log.d("Checkkkkkkkkkkkkkkkkkkkk", idCategory + nameCategory);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }
    @Override
    public void onClick(View view, int position) {

    }

    //Ko dùng
    @Override
    public void onLongClick(View view, int position) {
    }
}
