package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    UserDao userDao;
    OrderDao orderDao;
    DishDao dishDao;
    DetailOrderDao detailOrderDao;
    DishCategoryDao dishCategoryDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = new UserDao(this);
        userDao.getAllRuntime();

    }
    public void moveToSignUp(View view) {
        if (LibraryClass.checkConnection(this)){
            return;
        }
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void moveToSignIn(View view) {
        if (LibraryClass.checkConnection(this)){
            return;
        }
        startActivity(new Intent(this, SignInActivity.class));
    }
}
