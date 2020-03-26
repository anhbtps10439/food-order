package com.pro1121.foodorder.activity.UserCase;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.UserCase.fragment.CategoryUserFragment;
import com.pro1121.foodorder.activity.UserCase.fragment.HomeUserFragment;
import com.pro1121.foodorder.activity.UserCase.fragment.ProfileFragment;

public class UserCaseActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("User Case");
        init();
    }

    private void init() {
        chipNavigationBar = findViewById(R.id.navUserCase);
        chipNavigationBar.setOnItemSelectedListener(onItemSelectedListener);
        chipNavigationBar.setItemSelected(R.id.navHomeUser,true);
    }

    private ChipNavigationBar.OnItemSelectedListener onItemSelectedListener = new ChipNavigationBar.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int i) {
            switch (i){
                case R.id.navHomeUser:
                    Toast.makeText(UserCaseActivity.this, "Trang chính", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navCategoryUser:
                    Toast.makeText(UserCaseActivity.this, "Danh mục", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navProfile:
                    Toast.makeText(UserCaseActivity.this, "Người dùng", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
