package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pro1121.foodorder.R;

public class ChangePassWord extends AppCompatActivity {
    EditText et_old_pass, et_new_pass, et_confirm_pass;
    // Kiểm tra người dùng muốn đổi mật khẩu hay muốn tạo tài khoản mới
    // check = true => người dùng quên mật khẩu, không yêu cầu mật khẩu cũ
    // check = false => người dùng muốn đổi mật khẩu, yêu cầu mật khẩu cũ
    boolean check = getIntent().getBooleanExtra("isForgot", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);

        if (check) {
            // người dùng quên mật khẩu, không yêu cầu mật khẩu cũ
            EditText et_old_pass = findViewById(R.id.et_old_pass);
            et_old_pass.setVisibility(View.GONE);
        }

        et_old_pass = findViewById(R.id.et_old_pass);
        et_new_pass = findViewById(R.id.et_new_pass);
        et_confirm_pass = findViewById(R.id.et_confirm_pass);
    }

    public void checkChangePass(View view) {
        if (check == true) {
            if (et_new_pass.getText().toString().equalsIgnoreCase(et_confirm_pass.getText().toString()+"")) {
                // Set mật khẩu của sdt thành mật khẩu mới
                // -> Ở đây

                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                //Nếu đổi từ "Quên mật khẩu" thì quay lại màn hình đăng nhập, không thì quay lại profile
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();//Không cho người dùng back lại
            } else {
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                et_new_pass.requestFocus();
            }

        } else {
            // Nếu đổi mật khẩu theo chủ ý thì phải xác nhận lại mật khẩu cũ
            if (check == false) {
                // Kiểm tra mật khẩu cũ có khớp hay không
                // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
                // Set mật khẩu của sdt thành mật khẩu mới
                // -> Ở đây
                finish();
            }
        }
    }
}
