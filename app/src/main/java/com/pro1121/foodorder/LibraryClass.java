package com.pro1121.foodorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.model.DishCategoryModel;
import com.pro1121.foodorder.model.DishModel;
import com.pro1121.foodorder.model.OrderModel;
import com.pro1121.foodorder.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class LibraryClass {

    public static ArrayList<UserModel> userModelList = new ArrayList<>();
    public static ArrayList<DishModel> dishModelList = new ArrayList<>();
    public static ArrayList<DishCategoryModel> dishCategoryModelList = new ArrayList<>();
    public static ArrayList<OrderModel> orderModelList = new ArrayList<>();
    public static ArrayList<Integer> priceList = new ArrayList<>();

    public static ArrayList<DishModel> buyList = new ArrayList<>();
    public static ArrayList<Integer> buyAmountList = new ArrayList<>();

    public static ArrayList<Bitmap> categoryPicList = new ArrayList<>();
    public static ArrayList<Bitmap> dishPicList = new ArrayList<>();
    public static ArrayList<Bitmap> userPicList = new ArrayList<>();

    public static ArrayList<DishModel> dishListById = new ArrayList<>();

    public static String downloadURL;

    //chụp ảnh và gallery
    public static Bitmap currrentPhoto;
    public static String photoPath;
    public static Uri photoUri;

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
//    public static String convertImgToString(Context context, Bitmap image){
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 50, baos);
//        byte[] b = baos.toByteArray();
//        String encoded = Base64.encodeToString(b,Base64.DEFAULT);
//        return encoded;
//    }
//
  //  public static Bitmap convertStringToImg(String s){
  //      byte[] b = Base64.decode(s,Base64.DEFAULT);
   //     Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
  //      return bitmap;
 //  }

    public static ArrayList<DishModel> dishFilter(String categoryID)
    {
        ArrayList<DishModel> data = new ArrayList<>();
        for (int i = 0; i < dishModelList.size(); i++) {
                if (dishModelList.get(i).getDishCategoryId().equals(categoryID))
                {
                    data.add(dishModelList.get(i));
            }
        }
        return data;
    }

    public static  ArrayList<Bitmap> dishPicFilter(String categoryID) {
        ArrayList<Bitmap> data = new ArrayList<>();

        for (int i = 0; i < dishModelList.size(); i++) {
            if (dishModelList.get(i).getDishCategoryId().equals(categoryID)) {
                data.add(dishPicList.get(i));
            }
        }
            return data;
    }

    public static String createFileName()
    {
        return new SimpleDateFormat("ddMMyyyy_hhmmss_").format(new Date());
    }

    //upload bằng bytes[]
    //return một downloadURL
    public static void  photoUpload(final Context context, Bitmap photo, final DishModel dishModel)
    {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //storageReferece đóng vai trò như đường dẫn đến file
        //getLastPathSegment để lấy địa chỉ cuối cùng của file, ở đây là file name
        String location = "images/"+createFileName()+".jpg";
        final StorageReference storageReference = storage.getReference(location);

        byte[] convertedPhoto = convertToBytes(photo);

        storageReference.putBytes(convertedPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri uri = uriTask.getResult();
                String imageUrl = uri.toString();

                DishDao dishDao = new DishDao(context);
                dishDao.update(dishModel.getId(),dishModel.getDishCategoryId(),dishModel.getName(),dishModel.getPrice(),dishModel.getDes(),imageUrl);
            }
        });
    }

    //convert bitmap sang byte để upload
    public static byte[] convertToBytes(Bitmap inputBitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        inputBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    //convert uri sang bitmap để set vào imageview
    public static Bitmap convertToBitmap(Context context, Uri inputUri)
    {
        try
        {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), inputUri);
            return bitmap;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadPhoto(String url, final Context context, final String whereToAdd)
    {
        try
        {
            //Reference
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl(url);
            //convert bytes sang bitmap
            final long LIMITSIZE = 10 * 1024 * 1024;//10mb
            storageReference.getBytes(LIMITSIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    //offset là chỉ số index bắt đầu decode, length là độ dài của mảng bytes
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                    switch (whereToAdd)
                    {
                        case "category":{
                            categoryPicList.add(bitmap);
                            break;
                        }

                        case "dish":{
                            dishPicList.add(bitmap);
                            break;
                        }

                        case "user":{
                            userPicList.add(bitmap);
                            break;
                        }
                    }

                    Toast.makeText(context, "Download Successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
