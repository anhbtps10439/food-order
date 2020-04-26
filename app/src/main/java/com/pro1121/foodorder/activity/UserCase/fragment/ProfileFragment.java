package com.pro1121.foodorder.activity.UserCase.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pro1121.foodorder.model.OrderModel;
import com.pro1121.foodorder.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


import static com.pro1121.foodorder.LibraryClass.orderModelList;
import static com.pro1121.foodorder.LibraryClass.priceList;

import static com.pro1121.foodorder.activity.SignInOut.SignInActivity.currentUser;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private CircleImageView circleImageView;
    private TextView tv_greeting, tv_display_name, tv_order_history, tv_profile, tv_change_password, tv_sign_out;
    private ArrayList<OrderModel> orderHistoryList;
    private ArrayList<Integer> orderHistoryPriceList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setColorToolbarAndStatusBar(toolbar);
        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        setHasOptionsMenu(true);

        circleImageView = view.findViewById(R.id.crc_profile_image_user);
        tv_greeting =view.findViewById(R.id.tv_greeting);
        tv_display_name = view.findViewById(R.id.tv_display_name);
        tv_order_history = view.findViewById(R.id.tv_order_history);
        tv_profile = view.findViewById(R.id.tv_profile);
        tv_change_password = view.findViewById(R.id.tv_change_password);
        tv_sign_out = view.findViewById(R.id.tv_sign_out);

        tv_profile.setOnClickListener(this);
        tv_order_history.setOnClickListener(this);
        tv_change_password.setOnClickListener(this);
        tv_sign_out.setOnClickListener(this);

        UserModel user = currentUser;
        tv_greeting.setText("Xin chào "+user.getName());
        tv_display_name.setText(user.getName());
        try {
            Picasso.get().load(currentUser.getImage()).fit().into(circleImageView);
        }catch (Exception e){
            //Set ảnh bị lỗi hoặc chưa có ảnh
            circleImageView.setBackgroundColor(Color.BLACK);
            Toast.makeText(getActivity(), "Chưa có ảnh hoặc lỗi ảnh, Log d to check", Toast.LENGTH_SHORT).show();
            Log.d("Set image errorrrrrrrrrrrrrrrrrrrrrrrrrrrrr", e.toString());
        }

        orderHistoryFilter(user.getId());

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_profile:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_user_case, new EditProfileUserFragment()).commit();
                break;
            case R.id.tv_order_history:
                //Show lịch sử order
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_user_case, new OrderHistoryFragment(orderHistoryList, orderHistoryPriceList)).commit();

                break;
            case R.id.tv_change_password:
                startActivity(new Intent(getActivity(), ChangePassWord.class));
                break;
            case R.id.tv_sign_out:
                startActivity(new Intent(getActivity(), SignInActivity.class));
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        toolbar.setBackgroundColor(Color.parseColor("#FF3737"));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");

        orderHistoryPriceList = new ArrayList<>();
        orderHistoryList = new ArrayList<>();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_profile,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_setting_profile:
                Toast.makeText(getActivity(), "Updating!!!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FF3737"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FF3737"));
    }


    private void orderHistoryFilter(String id)
    {
        for (int i = 0; i < orderModelList.size(); i++)
        {
            if (orderModelList.get(i).getUserId().equals(id))
            {
                orderHistoryList.add(orderModelList.get(i));
                orderHistoryPriceList.add(priceList.get(i));
            }
        }
    }



}
