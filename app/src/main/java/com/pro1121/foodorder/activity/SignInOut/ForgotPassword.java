package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pro1121.foodorder.R;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
    public void moveToChangePass(View view){
        Intent intent = new Intent(ForgotPassword.this, ChangePassWord.class);
        startActivity(intent);
    }

    public void pleaseCallHelp(View view) {
        Toast.makeText(this, "Vui lòng gọi cho đội kỹ thuật", Toast.LENGTH_LONG).show();
    }
}
