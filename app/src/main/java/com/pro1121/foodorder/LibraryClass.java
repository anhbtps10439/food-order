package com.pro1121.foodorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telecom.ConnectionService;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.net.ConnectivityManagerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
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
    public static String downloadURL;

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

    //upload bằng bytes[]
    //return một downloadURL
    public static String  photoUpload(final Context context, byte[] photo)
    {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //storageReferece đóng vai trò như đường dẫn đến file
        //getLastPathSegment để lấy địa chỉ cuối cùng của file, ở đây là file name
        final StorageReference storageReference = storage.getReference("images/");


        //upload
        storageReference.putBytes(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //khi success thì lấy downloadURL
                //getURL
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downloadURL = uri.toString();
                    }
                });

                Toast.makeText(context, "Upload Successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "A problem happened! Please try again later.", Toast.LENGTH_SHORT).show();
                Log.e("uploadFailure", e.getMessage());
            }
        });

        return downloadURL;
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

    //hàm lưu file ảnh, đặt tên bằng ngày giờ chụp
    //File này chưa có data ảnh, chỉ có tên, đường dẫn
    //hàm này sẽ được gọi để tạo ra một file chứa thống tin trên, sau đó để vào Uri rồi cho vào intent
    public static File createPhotoFile(Context context, String photoPath) throws IOException
    {
        String takenDate = new SimpleDateFormat("ddMMyyyy_hhmmss_").format(new Date());

        //lấy đường dẫn của riêng app, app khác sẽ không truy cập được
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //tạo file
        //các tham số là (tên file, định dạng, đường dẫn)
        File photoFile = File.createTempFile(takenDate, ".jpg", storageDir);

        //lưu đường dẫn của file
        photoPath = photoFile.getAbsolutePath();

        return photoFile;
    }

    public static void openCam(Context context, Uri photoUri, final int REQUEST_IMAGE_CAPTURE, Activity activity, String photoPath)
    {
        //Khai báo một Intent thực hiện việc gọi một activity chụp ảnh
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // câu lệnh if này để kiểm tra xem hệ thống có Activity nào để chụp ảnh không
        //vì nếu gọi intent mà không có activity nào hoạt động thì ứng dụng sẽ bị crash
        if (intent.resolveActivity(context.getPackageManager()) != null )
        {
            //tạo một file trước
            File photoFile = null;

            try
            {
                //sau khi gọi createPhotoFile, photoPath đã có data đường dẫn
                photoFile = createPhotoFile(context, photoPath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            //nếu photoFile đã được tạo thành công
            if (photoFile != null)
            {
                //gán Uri cho currentPhotoUri
                photoUri = FileProvider.getUriForFile(context, "com.example.android.fileprovider", photoFile);

                //đặt Uri vào intent
                //vì action của intent là ACTION_IMAGE_CAPTURE
                //nên cái extra có key EXTRA_OUTPUT này sẽ giúp camera lưu hình ảnh vào currentPhotoUri
                //và Intent data ở onActivityResult sẽ null, vì file ảnh đã được lưu trong path rồi

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }


        }
    }

}
