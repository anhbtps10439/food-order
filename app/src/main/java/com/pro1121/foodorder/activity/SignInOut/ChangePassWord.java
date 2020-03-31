package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pro1121.foodorder.R;

public class ChangePassWord extends AppCompatActivity {
    //Điều kiện để đổi password
    //boolean check = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);
    }
    public void checkChangePass(View view){
        if (true){
            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            //Nếu đổi từ "Quên mật khẩu" thì quay lại màn hình đăng nhập, không thì quay lại profile
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();//Không cho người dùng back lại
        }else {
            Toast.makeText(this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}
