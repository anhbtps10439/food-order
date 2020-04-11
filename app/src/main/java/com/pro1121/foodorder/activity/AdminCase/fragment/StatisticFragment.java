package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro1121.foodorder.R;

public class StatisticFragment extends Fragment {
    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
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
                Fragment fragment = getFragmentManager().findFragmentByTag("statistic");
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
        toolbar.setBackgroundColor(Color.parseColor("#FF3737"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FF3737"));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setNavigationIcon(null);
    }
}
