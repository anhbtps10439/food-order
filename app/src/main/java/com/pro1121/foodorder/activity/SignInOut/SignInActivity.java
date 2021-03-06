package com.pro1121.foodorder.activity.SignInOut;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.AdminCaseActivity;
import com.pro1121.foodorder.activity.UserCase.UserCaseActivity;
import com.pro1121.foodorder.model.UserModel;

public class SignInActivity extends AppCompatActivity {
    EditText et_sdt, et_password;
    CheckBox chkRemember;
    public static UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        et_sdt = findViewById(R.id.et_sdt);
        et_password = findViewById(R.id.et_password);

        chkRemember = findViewById(R.id.checkBox);

        restorePassword();
        rememberPassword();


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

        if (!LibraryClass.isOnline(this)){
            return;
        }

        //check validate
        if (et_sdt.getText().toString().equals(""))
        {
            Toast.makeText(this, "Vui lòng nhập số điện thoại đã đăng kí!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (et_password.getText().toString().equals(""))
            {
                Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }
        else if(!chkRemember.isChecked()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
            builder.setTitle("Nhớ mật khẩu")
                    .setMessage("Bạn có tôi muốn ghi nhớ tài khoản của mình cho lần đăng nhập sau")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                            chkRemember.setChecked(true);
                            rememberPassword();
                            // Kiểm tra tài khoản bao gồm sdt, password, role
                            signIn();

                        }
            })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    signIn();
                }
            }).show();
        }else {
            signIn();
        }


    }
    public void signIn() {

        final String sdt = et_sdt.getText().toString()+"";
        final String pass = et_password.getText().toString()+"";

        for (int i = 0; i < LibraryClass.userModelList.size(); i++) {
            //Admin
            if (LibraryClass.userModelList.get(i).getId().equalsIgnoreCase(sdt)
                    && LibraryClass.userModelList.get(i).getPassword().equalsIgnoreCase(pass)
                    && LibraryClass.userModelList.get(i).getRole().equalsIgnoreCase("admin")) {
                currentUser = LibraryClass.userModelList.get(i);
                startActivity(new Intent(SignInActivity.this, AdminCaseActivity.class));
                et_sdt.setText("");
                et_password.setText("");
                Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                return;
            }
            //User
            if (LibraryClass.userModelList.get(i).getId().equalsIgnoreCase(sdt)
                    && LibraryClass.userModelList.get(i).getPassword().equalsIgnoreCase(pass)
                    && LibraryClass.userModelList.get(i).getRole().equalsIgnoreCase("user")) {
                currentUser = LibraryClass.userModelList.get(i);
                startActivity(new Intent(SignInActivity.this, UserCaseActivity.class));
                et_sdt.setText("");
                et_password.setText("");
                Toast.makeText(SignInActivity.this, "Đăng nhập thành công " + currentUser.getName(), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // Nếu kiểm tra không có ai có thông tin trùng khớp
        et_sdt.setText("");
        et_password.setText("");
        Toast.makeText(SignInActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        chkRemember.setChecked(false);

    }
    //Bấm nút remember
    public void rememberPassword()
    {
        //tạo sharePreference

        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String user = et_sdt.getText().toString();
        String pass = et_password.getText().toString();
        boolean check = chkRemember.isChecked(); // check là true, không check là false

        if (check)
        {
            editor.putString("id", user);
            editor.putString("pass", pass);
            editor.putBoolean("check", check);
        }
        else
        {
            editor.clear();
        }

        editor.commit();
    }

    public void restorePassword()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        //mặc định là ko check vào ô
        boolean chk = sharedPreferences.getBoolean("check", false);

        //nếu đã chọn lưu trước đó, phục hồi lại các trạng thái đăng nhập
        if (chk)
        {
            et_sdt.setText(sharedPreferences.getString("id", ""));
            et_password.setText(sharedPreferences.getString("pass", ""));
        }
        chkRemember.setChecked(chk);
    }
}
