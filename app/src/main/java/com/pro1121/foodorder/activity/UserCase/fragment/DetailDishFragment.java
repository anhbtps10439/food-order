package com.pro1121.foodorder.activity.UserCase.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.activity.UserCase.UserCaseActivity;

import static com.pro1121.foodorder.LibraryClass.dishModelList;
import static com.pro1121.foodorder.activity.UserCase.UserCaseActivity.position_dish_detail;

import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;


public class DetailDishFragment extends Fragment {
    private Toolbar toolbar;
    private TextView tvDishName, tvPrice, tvDes;
    private Button btnAddCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_click_food, container, false);

        tvDishName = view.findViewById(R.id.tv_dish_name_detail);
        tvPrice = view.findViewById(R.id.tv_dish_price_detail);
        tvDes = view.findViewById(R.id.tv_dish_des_detail);
        btnAddCart = view.findViewById(R.id.btn_add_cart);

        tvDishName.setText(dishModelList.get(position_dish_detail).getName());
        tvDes.setText(dishModelList.get(position_dish_detail).getDes());
        tvPrice.setText(dishModelList.get(position_dish_detail).getPrice()+"");

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Updating", Toast.LENGTH_SHORT).show();
            }
        });
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_user_case, new DishUserFragment()).commit();
            }
        });
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onResume() {
        super.onResume();
        setColorToolbarAndStatusBar(toolbar);
    }
}
