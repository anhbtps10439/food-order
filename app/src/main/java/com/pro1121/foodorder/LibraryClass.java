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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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

public class LibraryClass {

    public static ArrayList<UserModel> userModelList = new ArrayList<>();
    public static ArrayList<DishModel> dishModelList = new ArrayList<>();
    public static ArrayList<DishCategoryModel> dishCategoryModelList = new ArrayList<>();
    public static ArrayList<OrderModel> orderModelList = new ArrayList<>();
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
    public static String convertImgToString(Context context, Bitmap image){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] b = baos.toByteArray();
        String encoded = Base64.encodeToString(b,Base64.DEFAULT);
        return encoded;
    }

    public static Bitmap convertStringToImg(String s){
        byte[] b = Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }

    public static String createFileName()
    {
        return new SimpleDateFormat("ddMMyyyy_hhmmss_").format(new Date());
    }

    //upload bằng bytes[]
    //return một downloadURL
    public static String  photoUpload(final Context context, byte[] photo)
    {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //storageReferece đóng vai trò như đường dẫn đến file
        //getLastPathSegment để lấy địa chỉ cuối cùng của file, ở đây là file name
        final StorageReference storageReference = storage.getReference("images/"+createFileName()+".jpg");


        //upload
        UploadTask uploadTask = storageReference.putBytes(photo);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Upload Succeed!", Toast.LENGTH_SHORT).show();
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful())
                {
                    throw task.getException();
                }

                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful())
                {
                    photoUri = task.getResult();
                }
                else
                {
                    Toast.makeText(context, "Can't get the URL!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return photoUri.toString();
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



}
