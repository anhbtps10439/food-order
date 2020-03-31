package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pro1121.foodorder.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
