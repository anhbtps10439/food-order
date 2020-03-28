package com.pro1121.foodorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pro1121.foodorder.R;

public class SignUpActivity extends AppCompatActivity {
    //Check xem sdt đã được đăng ký hay chưa
    boolean condition = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void checkSignUp(View view) {
        if (condition==true){
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();// không cho người dùng back lại sau khi đăng ký thành công
        }
    }

    public void moveToSignIn(View view) {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
}
