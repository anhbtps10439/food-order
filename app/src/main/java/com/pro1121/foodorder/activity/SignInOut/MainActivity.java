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
import android.view.View;
import android.widget.Toast;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.dao.DetailOrderDao;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.dao.OrderDao;
import com.pro1121.foodorder.dao.UserDao;
import com.pro1121.foodorder.service.AdminNotifyService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UserDao userDao;
    OrderDao orderDao;
    DishDao dishDao;
    DetailOrderDao detailOrderDao;
    DishCategoryDao dishCategoryDao;
    public static ArrayList<Drawable> dishs_img = new ArrayList<>();
    public static final int REQUEST_PERMISSION_CODE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = new UserDao(this);
        userDao.getAllRuntime();
        orderDao = new OrderDao(this);
        orderDao.getAllRuntime();
        dishDao = new DishDao(this);
        dishDao.getAllRuntime();
        dishCategoryDao = new DishCategoryDao(this);
        dishCategoryDao.getAllRuntime();
        detailOrderDao = new DetailOrderDao(this);


        if (!permissionCheck(MainActivity.this))
        {
            permisssionResquest(MainActivity.this);
        }


//        if (!isServiceRunning(new AdminNotifyService().getClass()))
//        {
//            Intent intent = new Intent(MainActivity.this, AdminNotifyService.class);
//            startService(intent);
//        }
    }

    private boolean isServiceRunning(Class<?> serviceClass)
    {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo serviceInfo:activityManager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(serviceInfo.getClass().getName()))
            {
                return true;
            }
        }
        Toast.makeText(this, "Service is not running!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void moveToSignUp(View view) {
        if (!LibraryClass.isOnline(this)){
            return;
        }
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void moveToSignIn(View view) {
        if (!LibraryClass.isOnline(this)){
            return;
        }
        startActivity(new Intent(this, SignInActivity.class));
    }

    public static boolean permissionCheck(Context context)
    {
        //if permission is not granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }

        return true;
    }



    public static void permisssionResquest(Activity activity)
    {

        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case REQUEST_PERMISSION_CODE: {
                //if permission is not granted, permission array will be empty
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                    Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
