package com.pro1121.foodorder.activity.AdminCase;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.fragment.BillFragment;

import com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment;
import com.pro1121.foodorder.activity.AdminCase.fragment.HomeAdminFragment;
import com.pro1121.foodorder.activity.AdminCase.fragment.StatisticFragment;
import com.pro1121.foodorder.activity.AdminCase.fragment.UserManagerFragment;

public class AdminCaseActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //Ánh xạ
        init();


    }

    private void init() {
        chipNavigationBar = findViewById(R.id.navAdminCase);
        chipNavigationBar.setOnItemSelectedListener(onItemSelectedListener);
        chipNavigationBar.setItemSelected(R.id.navHome, true);
    }

    private ChipNavigationBar.OnItemSelectedListener onItemSelectedListener = new
            ChipNavigationBar.OnItemSelectedListener() {
                @Override
                public void onItemSelected(int i) {
                    FragmentManager fm = getSupportFragmentManager();
                    switch (i) {
                        case R.id.navHome:
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new HomeAdminFragment()).commit();
                            break;
                        case R.id.navCategory:
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new CategoryAdminFragment()).commit();
                            break;
                        case R.id.navBill:
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new BillFragment()).commit();
                            break;
                        case R.id.navStatistic:
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new StatisticFragment()).commit();
                            break;
                        case R.id.navUserManager:
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new UserManagerFragment()).commit();
                            break;
                    }
                }
            };
}
