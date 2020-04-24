package com.pro1121.foodorder.activity.SignInOut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.dao.DetailOrderDao;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.dao.OrderDao;
import com.pro1121.foodorder.dao.UserDao;
import com.pro1121.foodorder.model.ColorDishCaterogyModel;
import com.pro1121.foodorder.service.AdminNotifyService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    UserDao userDao;
    OrderDao orderDao;
    DishDao dishDao;
    DetailOrderDao detailOrderDao;
    DishCategoryDao dishCategoryDao;
    public static final int REQUEST_PERMISSION_CODE = 5;
    private ColorDishCaterogyModel c,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12;
    public static final List<ColorDishCaterogyModel> colorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Loading
        userDao = new UserDao(this);
        userDao.getAllRuntime();
        dishCategoryDao = new DishCategoryDao(this);
        dishCategoryDao.getAllRuntime();
        dishDao = new DishDao(this);
        dishDao.getAllRuntime();

//        LibraryClass.loadAllImg(this);

        if (!permissionCheck(MainActivity.this)) {
            permisssionResquest(MainActivity.this);
        }

//        if (!isServiceRunning(new AdminNotifyService().getClass()))
//        {
//            Intent intent = new Intent(MainActivity.this, AdminNotifyService.class);
//            startService(intent);
//        }
        addColorToList();
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(serviceInfo.getClass().getName())) {
                return true;
            }
        }
        Toast.makeText(this, "Service is not running!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void moveToSignUp(View view) {
        if (!LibraryClass.isOnline(this)) {
            return;
        }
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void moveToSignIn(View view) {
        if (!LibraryClass.isOnline(this)) {
            return;
        }
        startActivity(new Intent(this, SignInActivity.class));
    }

    public static boolean permissionCheck(Context context) {
        //if permission is not granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }


    public static void permisssionResquest(Activity activity) {

        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                //if permission is not granted, permission array will be empty
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void addColorToList() {
        c = new ColorDishCaterogyModel("#0CCDA3");
        c1 = new ColorDishCaterogyModel("#F9957F");
        c2 = new ColorDishCaterogyModel("#0AC9E2");
        c3 = new ColorDishCaterogyModel("#A3C9E2");
        c4 = new ColorDishCaterogyModel("#FCFC57");
        c5 = new ColorDishCaterogyModel("#FEA858");
        c6 = new ColorDishCaterogyModel("#FF82D8");
        c7 = new ColorDishCaterogyModel("#FF82D8");
        c8 = new ColorDishCaterogyModel("#FF82D8");
        c9 = new ColorDishCaterogyModel("#FF82D8");
        c10 = new ColorDishCaterogyModel("#FF82D8");
        c11 = new ColorDishCaterogyModel("#FF82D8");
        c12 = new ColorDishCaterogyModel("#FF82D8");

        colorList.add(c);
        colorList.add(c1);
        colorList.add(c2);
        colorList.add(c3);
        colorList.add(c4);
        colorList.add(c5);
        colorList.add(c6);
        colorList.add(c7);
        colorList.add(c8);
        colorList.add(c9);
        colorList.add(c10);
        colorList.add(c11);
        colorList.add(c12);
    }
}
