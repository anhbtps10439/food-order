package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.pro1121.foodorder.R;

public class OrderManagerFragment extends Fragment {
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_manager,container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarAdminCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_white,null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getFragmentManager().findFragmentByTag("order_manager");
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setColorToolbarAndStatusBar(toolbar);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar){
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorRedMain,null));
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorRedMain,null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setNavigationIcon(null);
    }
}