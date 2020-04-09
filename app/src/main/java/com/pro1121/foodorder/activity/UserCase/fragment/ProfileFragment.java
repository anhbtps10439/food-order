package com.pro1121.foodorder.activity.UserCase.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.SignInOut.ChangePassWord;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.activity.SignInOut.SignInActivity;
import com.pro1121.foodorder.model.UserModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private CircleImageView circleImageView;
    private TextView tv_greeting, tv_display_name, tv_order_history, tv_profile, tv_change_password, tv_sign_out;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FF3737"));
        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        setHasOptionsMenu(true);

        circleImageView = view.findViewById(R.id.profile_image);
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

        UserModel user = SignInActivity.currentUser;
        tv_greeting.setText("Xin chào "+user.getName());
        tv_display_name.setText(user.getName());

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_profile:
                //Show thông tin cá nhân
                break;
            case R.id.tv_order_history:
                //Show lịch sử order
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

    @Override
    public void onDestroyView() {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        super.onDestroyView();
    }


}
