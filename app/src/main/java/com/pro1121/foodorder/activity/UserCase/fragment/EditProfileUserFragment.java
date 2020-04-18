package com.pro1121.foodorder.activity.UserCase.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment;

public class EditProfileUserFragment extends Fragment {

    private TextView tv_edit_profile;
    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setColorToolbarAndStatusBar(toolbar);
        View view = inflater.inflate(R.layout.fragment_edit_information,container,false);
        tv_edit_profile = view.findViewById(R.id.tv_edit_profile);
        tv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });
        return view;
    }

    private void editUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_profile_user, null, false);
        builder.setTitle("Sửa thông tin")
                .setView(view).setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Updating!!!", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_user_case, new ProfileFragment()).commit();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setNavigationIcon(null);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }
}
