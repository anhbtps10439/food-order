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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
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
    public static ArrayList<Bitmap> categoryPicList = new ArrayList<>();
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
//    public static Bitmap convertStringToImg(String s){
//        byte[] b = Base64.decode(s,Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
//        return bitmap;
//    }

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

    public static String createFileName()
    {
        return new SimpleDateFormat("ddMMyyyy_hhmmss_").format(new Date());
    }

    //upload bằng bytes[]
    //return một downloadURL
    public static String  photoUpload(final Context context, Bitmap photo)
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
                Toast.makeText(context, "Upload Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(context, "Upload Completely", Toast.LENGTH_SHORT).show();
            }
        });
        
        return "gs://food-order-2-e2475.appspot.com/"+location;
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

    private static void downloadPhoto(String url, final Context context)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(url);
        //convert bytes sang bitmap
        final long LIMITSIZE = 10 * 1024 * 1024;//10mb
        storageReference.getBytes(LIMITSIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                //offset là chỉ số index bắt đầu decode, length là độ dài của mảng bytes
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                categoryPicList.add(bitmap);
                Toast.makeText(context, "Download Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Load toàn bộ ảnh của DishCategory -> Huy
    public static void loadAllImg(final Context context) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReferenceFromUrl("gs://food-order-34040.appspot.com");
        // Làm mới mảng chứa bitmap
        categoryPicList.clear();
        // Gọi reference đến thư mục images trên firebase
        StorageReference imgRef = reference.child("images");
        // List All file trong images
        imgRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            // Khi load hết thành công sẽ trả về listResult chứa các file
            @Override
            public void onSuccess(ListResult listResult) {
                // Trỏ đến từng item trong listResult
                for (StorageReference item : listResult.getItems()){
                    // getBytes của từng item để dùng BitmapFactory parse ra Bitmap
                    item.getBytes(10 * 1024 * 1024)
                            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Parse ra Bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            categoryPicList.add(bitmap);
                        }
                    });
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static void downloadPhotoToArrayList(Context context)
    {
        Log.d("DishCate Listtttttttttttttttttttt", dishCategoryModelList.size()+"") ;
        if (dishCategoryModelList.size() > 0)

        {
            for (int i = 0; i < dishCategoryModelList.size(); i++)
            {
                downloadPhoto(dishCategoryModelList.get(i).getImage(), context);
            }
        }
        Log.d("PicListSize", "" + categoryPicList.size());
    }

}
