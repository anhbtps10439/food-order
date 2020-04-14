package com.pro1121.foodorder.activity.AdminCase.fragment;

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

import com.pro1121.foodorder.R;


public class HomeAdminFragment extends Fragment {

    Toolbar toolbar;
    private ImageButton btn_dish_likest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);

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
    public void setColorToolbarAndStatusBar(Toolbar toolbar){
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }
}
