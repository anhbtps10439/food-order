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
import com.pro1121.foodorder.dao.OrderDao;

public class AdminCaseActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Toolbar toolbar;
    private long backTime;

    public static int idCategory;
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

        //lấy data order
        OrderDao orderDao = new OrderDao(this);
        orderDao.getAllRuntime();
    }

    //ẩn title tool bar
    private void hideTitleToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
    }


    private void init() {
        chipNavigationBar = findViewById(R.id.navAdminCase);
        toolbar = findViewById(R.id.toolbarAdminCase);

    }
    // BottomNavgation
    //Sử  dụng thử viện bên ngoài
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

    //back 2 time to exit
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
