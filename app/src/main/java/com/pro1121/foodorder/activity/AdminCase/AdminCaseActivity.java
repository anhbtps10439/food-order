package com.pro1121.foodorder.activity.AdminCase;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;

import com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment;
import com.pro1121.foodorder.activity.AdminCase.fragment.HomeAdminFragment;
import com.pro1121.foodorder.activity.AdminCase.fragment.StoreFragment;

public class AdminCaseActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Toolbar toolbar;
    private long backTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.HomeTheme);
        setContentView(R.layout.activity_admin);
        //Ánh xạ
        init();
        hideTitleToolbar();

        chipNavigationBar.setOnItemSelectedListener(onItemSelectedListener);
        //open tab home first
        chipNavigationBar.setItemSelected(R.id.navHome, true);
    }

    private void hideTitleToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
    }


    private void init() {
        chipNavigationBar = findViewById(R.id.navAdminCase);
        toolbar = findViewById(R.id.toolbarAdminCase);

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
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new CategoryAdminFragment(AdminCaseActivity.this)).commit();
                            break;
                        case R.id.navUserManager:
                            fm.beginTransaction().replace(R.id.nav_host_admin_case, new StoreFragment()).commit();
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
