package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.AdminCaseActivity;
import com.pro1121.foodorder.activity.UserCase.UserCaseActivity;

public class SignInActivity extends AppCompatActivity {
    EditText et_sdt, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        et_sdt = findViewById(R.id.et_sdt);
        et_password = findViewById(R.id.et_password);

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
        String sdt = et_sdt.getText().toString();
        if (sdt.equalsIgnoreCase("1234567890")){
            startActivity(new Intent(this, AdminCaseActivity.class));
            et_sdt.setText("");
            et_password.setText("");
        }else {
            startActivity(new Intent(this, UserCaseActivity.class));
            et_sdt.setText("");
            et_password.setText("");
        }

    }
}
