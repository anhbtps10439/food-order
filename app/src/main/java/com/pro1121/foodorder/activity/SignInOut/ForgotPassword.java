package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;

public class ForgotPassword extends AppCompatActivity {
    EditText et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et_id = findViewById(R.id.et_id);
    }
    public void moveToChangePass(View view){
        if (!LibraryClass.isOnline(this)){
            return;
        }
        for (int i = 0; i<LibraryClass.userModelList.size();i++){
            if (LibraryClass.userModelList.get(i).getId().equalsIgnoreCase(et_id.getText().toString()+"")){
                Intent intent = new Intent(ForgotPassword.this, ChangePassWord.class);
                intent.putExtra("isForgot",true);
                startActivity(intent);
                finish();
            }
        }
        Toast.makeText(this, "Số điện thoại chưa được đăng ký", Toast.LENGTH_SHORT).show();

    }

    public void pleaseCallHelp(View view) {
        Toast.makeText(this, "Vui lòng gọi cho đội kỹ thuật", Toast.LENGTH_LONG).show();
    }
}
