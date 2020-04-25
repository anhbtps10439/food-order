package com.pro1121.foodorder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.provider.MediaStore;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;
import com.pro1121.foodorder.activity.SignInOut.SignInActivity;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.dao.DishDao;

import com.pro1121.foodorder.dao.UserDao;
import com.pro1121.foodorder.model.DishCategoryModel;
import com.pro1121.foodorder.model.DishModel;
import com.pro1121.foodorder.model.OrderModel;
import com.pro1121.foodorder.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static String createDate()
    {
        return new SimpleDateFormat("ddMMyyyy_HHmmss_").format(new Date());
    }

    //upload bằng bytes[]
    //return một downloadURL
    public static void  photoUpload(final Context context, Bitmap photo, final DishModel dishModel, final UserModel userModel,final String which)
    {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //storageReferece đóng vai trò như đường dẫn đến file
        //getLastPathSegment để lấy địa chỉ cuối cùng của file, ở đây là file name
        String location = "images/"+ createDate()+".jpg";
        final StorageReference storageReference = storage.getReference(location);

        byte[] convertedPhoto = convertToBytes(photo);

        storageReference.putBytes(convertedPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Lấy link của ảnh về
                Task<Uri> uriTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri uri = uriTask.getResult();
                String imageUrl = uri.toString();

                //lấy link hoàn tất thì cập nhật dish được truyền vào
                //NotifyDataChanged được gắn bên trong hàm update.
                switch (which){
                    case "dish":
                        DishDao dishDao = new DishDao(context);
                        dishDao.update(dishModel.getId(),dishModel.getDishCategoryId(),dishModel.getName(),dishModel.getPrice(),dishModel.getDes(),imageUrl);
                    break;
                    case "user":
                        UserDao userDao = new UserDao(context);
                        userDao.update(userModel.getId(),userModel.getName(),userModel.getBirthday(),userModel.getEmail(),userModel.getPassword(),userModel.getRole(),imageUrl);
                        SignInActivity.currentUser = new UserModel(userModel.getId(),userModel.getName(),userModel.getBirthday(),userModel.getEmail(),userModel.getPassword(),userModel.getRole(),imageUrl);
                }



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

}
