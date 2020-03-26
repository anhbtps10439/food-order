package com.pro1121.foodorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.dao.DetailOrderDao;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.dao.OrderDao;
import com.pro1121.foodorder.dao.UserDao;
import com.pro1121.foodorder.model.DishModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        OrderDao orderDao = new OrderDao(MainActivity.this);
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//        String d = sdf.format(date);
//
//        String orderId = orderDao.insert(d, "0123456789", "OKe okeoke");
//
//        DetailOrderDao detailOrderDao = new DetailOrderDao(MainActivity.this);
//        DishModel dishModel = new DishModel("TS-M3AzZdjbBpgdBFTAAP8", "TS", "Trà sữa truyền thống", 22000, "Trà sữa mang hương vị truyền thống đến từ Food Order", "Ảnh");
//        detailOrderDao.insert(orderId, dishModel, 2);


    }
}
