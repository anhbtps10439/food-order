package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.SignInOut.ChangePassWord;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.activity.SignInOut.SignInActivity;
import com.pro1121.foodorder.model.UserModel;

public class StoreFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView tv_greeting, tv_display_name, tv_order_manager, tv_statistic, tv_change_password, tv_sign_out;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_manager,container,false);
        //bắt buộc phải có để button trên toolbar hoạt động
        setHasOptionsMenu(true);

        tv_display_name = view.findViewById(R.id.tv_display_name);
        tv_order_manager = view.findViewById(R.id.tv_order_manager);
        tv_statistic = view.findViewById(R.id.tv_statistic);
        tv_change_password = view.findViewById(R.id.tv_change_password);
        tv_sign_out = view.findViewById(R.id.tv_sign_out);

        tv_order_manager.setOnClickListener(this);
        tv_statistic.setOnClickListener(this);
        tv_change_password.setOnClickListener(this);
        tv_sign_out.setOnClickListener(this);

        UserModel user = SignInActivity.currentUser;
        tv_display_name.setText(user.getName());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarAdminCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(Color.parseColor("#FF3737"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FF3737"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_order_manager:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.nav_host_admin_case, new OrderManagerFragment(),"order_manager").commit();
                break;
            case R.id.tv_statistic:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.nav_host_admin_case, new StatisticFragment(),"statistic").commit();
                break;
            case R.id.tv_change_password:
                startActivity(new Intent(getActivity(), ChangePassWord.class));
                break;
            case R.id.tv_sign_out:
                startActivity(new Intent(getActivity(), SignInActivity.class));
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_profile, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(Color.parseColor("#FF3737"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FF3737"));
    }
}