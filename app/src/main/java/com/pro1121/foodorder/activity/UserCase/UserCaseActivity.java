package com.pro1121.foodorder.activity.UserCase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.UserCase.fragment.CategoryUserFragment;
import com.pro1121.foodorder.activity.UserCase.fragment.HomeUserFragment;
import com.pro1121.foodorder.activity.UserCase.fragment.ProfileFragment;


public class UserCaseActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Toolbar toolbar;
    private long backTime;
    public static int positon_dish, position_dish_detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.HomeTheme);
        setContentView(R.layout.activity_user);
        init();
        hideToolbarTitle();
        //create bottom nav
        chipNavigationBar.setOnItemSelectedListener(onItemSelectedListener);
        chipNavigationBar.setItemSelected(R.id.navHomeUser,true);
    }


    private void hideToolbarTitle() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
    }

    private void init() {
        chipNavigationBar = findViewById(R.id.navUserCase);
        toolbar = findViewById(R.id.toolbarUserCase);
    }

    private ChipNavigationBar.OnItemSelectedListener onItemSelectedListener = new ChipNavigationBar.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int i) {
            FragmentManager fm = getSupportFragmentManager();
            switch (i){
                case R.id.navHomeUser:
                    fm.beginTransaction().replace(R.id.nav_host_user_case, new HomeUserFragment()).commit();

                    break;
                case R.id.navCategoryUser:
                    fm.beginTransaction().replace(R.id.nav_host_user_case, new CategoryUserFragment()).commit();
                    break;
                case R.id.navProfile:
                    fm.beginTransaction().replace(R.id.nav_host_user_case, new ProfileFragment()).commit();
                    break;
            }
        }
    };
    @Override
    public void onBackPressed() {
        if(backTime + 3000 > System.currentTimeMillis()){
            super.onBackPressed();
            onDestroy();
            return;
        }else {
            Toast.makeText(this, "Bấm thêm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        backTime = System.currentTimeMillis();
    }

}
