package com.pro1121.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

//cái này là Trang dùng để test những file Fragment.xml mà Trang làm,nó ko có gây ảnh hưởng gì tới project này đâu,mọi người thông cảm nhé
public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        Button show = findViewById(R.id.btn);
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TestActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//                v = inflater.inflate(R.layout.dialog_click_kind_food,null );
//                alertDialog.setView(v);
//
//                AlertDialog b = alertDialog.create();
//                b.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                b.show();
//            }
//        });

    }

}

