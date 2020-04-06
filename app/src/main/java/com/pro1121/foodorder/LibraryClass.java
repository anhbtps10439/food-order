package com.pro1121.foodorder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.telecom.ConnectionService;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.ConnectivityManagerCompat;

import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.model.DishCategoryModel;
import com.pro1121.foodorder.model.DishModel;
import com.pro1121.foodorder.model.OrderModel;
import com.pro1121.foodorder.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class LibraryClass {

    public static ArrayList<UserModel> userModelList = new ArrayList<>();
    public static ArrayList<DishModel> dishModelList = new ArrayList<>();
    public static ArrayList<DishCategoryModel> dishCategoryModelList = new ArrayList<>();
    public static ArrayList<OrderModel> orderModelList = new ArrayList<>();
    public static boolean isOnline(Context context){
        ConnectivityManager manager;
        manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = manager.getActiveNetwork();
        if (network==null){
            Toast.makeText(context, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static String convertImgToString(Context context, int id){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encoded = Base64.encodeToString(b,Base64.DEFAULT);
        return encoded;
    }

    public static Bitmap convertStringToImg(String s){
        byte[] b = Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }

}
