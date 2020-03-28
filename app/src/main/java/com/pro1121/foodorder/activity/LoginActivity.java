package com.pro1121.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pro1121.foodorder.R;

public class LoginActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void moveToSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void moveToSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }
}
