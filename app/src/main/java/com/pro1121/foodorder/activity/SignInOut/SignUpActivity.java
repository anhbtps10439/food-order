package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.dao.UserDao;
import com.pro1121.foodorder.model.UserModel;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    //Check xem sdt đã được đăng ký hay chưa
    UserDao dao = new UserDao(this);
    ArrayList<UserModel>list =LibraryClass.userModelList;
    EditText et_id, et_pass, et_confirm_pass, et_display_name;
    Button btn_sign_up;
    boolean condition = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_password);
        et_confirm_pass = findViewById(R.id.et_confirm_password);
        et_display_name = findViewById(R.id.et_display_name);
        btn_sign_up = findViewById(R.id.btn_sign_up);


    }

    public void checkSignUp(View view) {
        for (int i=0; i<list.size();i++){
            if (list.get(i).getId().equalsIgnoreCase(et_id.getText().toString())){
                Toast.makeText(this, "Số điện thoại đã được sử dụng", Toast.LENGTH_SHORT).show();
                break;
            }else {
                if (et_pass.getText().toString().equalsIgnoreCase(et_confirm_pass.getText().toString())){
                dao.insert(et_display_name.getText().toString(),
                        "",
                        et_id.getText().toString(),
                        "",
                        et_pass.getText().toString(),
                        "user");
                    Toast.makeText(this, LibraryClass.userModelList.size()+" user", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,SignInActivity.class);
                    startActivity(intent);
                    finish();// không cho người dùng back lại sau khi đăng ký thành công
                }else {
                    Toast.makeText(this, "Mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
                    et_pass.requestFocus();
                }
            }

        }
    }

    public void moveToSignIn(View view) {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
}
