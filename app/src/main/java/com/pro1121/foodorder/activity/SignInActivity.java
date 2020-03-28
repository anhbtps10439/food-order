package com.pro1121.foodorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.AdminCaseActivity;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }


    public void moveToSignUp(View view){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    public void moveToForgotPass(View view){
        Intent intent = new Intent(SignInActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void moveToMain(View view) {
        startActivity(new Intent(this, AdminCaseActivity.class));
    }
}
