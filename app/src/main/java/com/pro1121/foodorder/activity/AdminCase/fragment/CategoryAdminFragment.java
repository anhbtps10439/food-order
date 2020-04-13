package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.huyTest.Adapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.pro1121.foodorder.LibraryClass.convertImgToString;
import static com.pro1121.foodorder.LibraryClass.convertToBitmap;
import static com.pro1121.foodorder.LibraryClass.convertToBytes;
import static com.pro1121.foodorder.LibraryClass.currrentPhoto;
import static com.pro1121.foodorder.LibraryClass.dishModelList;
import static com.pro1121.foodorder.LibraryClass.downloadURL;
import static com.pro1121.foodorder.LibraryClass.photoPath;
import static com.pro1121.foodorder.LibraryClass.photoUpload;
import static com.pro1121.foodorder.LibraryClass.photoUri;


public class CategoryAdminFragment extends Fragment {

    private Context context;

    RecyclerView recyclerView;
    private FloatingActionButton fbCategory;
    private Toolbar toolbar;
    Adapter adapter;

    //dialog
    private ImageView ivCategoryAvatar;
    private String url;
    private AlertDialog alertDialog;
    private String categoryID;
    private String categoryName;
    private String categoryDes;

    public static final int REQUEST_CHOOSE_PHOTO_FROM_GALLERY = 2;
    public static final int REQUEST_IMAGE_CAPTURE = 1;


    public CategoryAdminFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_dish);
        fbCategory = view.findViewById(R.id.fbCategory);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(getActivity(), dishModelList);
        recyclerView.setAdapter(adapter);

        //Floating button
        fbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_insert_category, null, false));
                builder.setTitle("Thêm loại");
                builder.setPositiveButton("Thêm", null);
                
                
                alertDialog = builder.create();
                alertDialog.show();

                ivCategoryAvatar = alertDialog.findViewById(R.id.ivCategoryAvatarDialog);
                Button btnOpenCam = alertDialog.findViewById(R.id.btnOpenCamDialog);
                Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                final EditText etCategoryCode = alertDialog.findViewById(R.id.etCategoryCodeDialog);
                final EditText etCategoryName = alertDialog.findViewById(R.id.etCategoryNameDialog);
                final EditText etCategoryDes = alertDialog.findViewById(R.id.etCategoryDesDialog);
        
                //Click to open gallery
                ivCategoryAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Mở Gallery !!!", Toast.LENGTH_SHORT).show();
                        //intent mở gallery
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO_FROM_GALLERY);
                    }
                });
                //Click to open Camera
                btnOpenCam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Open Camera
                        Toast.makeText(getActivity(), "Mở Camera !!!", Toast.LENGTH_SHORT).show();
                        openCam();
                    }
                });
                
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Nút thêm!", Toast.LENGTH_SHORT).show();
                        categoryID = etCategoryCode.getText().toString();
                        categoryName = etCategoryName.getText().toString();
                        categoryDes = etCategoryDes.getText().toString();

                        byte[] photo = convertToBytes(currrentPhoto);

                        DishCategoryDao dao = new DishCategoryDao(context);
                        downloadURL = photoUpload(context, photo);
                        dao.insert(categoryID, categoryName, categoryDes, downloadURL);
                        alertDialog.dismiss();
                    }
                });
                
                

            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarAdminCase);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Loại thức ăn");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_category,menu);
    }


    //hàm lưu file ảnh, đặt tên bằng ngày giờ chụp
    //File này chưa có data ảnh, chỉ có tên, đường dẫn
    //hàm này sẽ được gọi để tạo ra một file chứa thống tin trên, sau đó để vào Uri rồi cho vào intent
    private File createPhotoFile() throws IOException
    {
        String takenDate = new SimpleDateFormat("ddMMyyyy_hhmmss_").format(new Date());

        //lấy đường dẫn của riêng app, app khác sẽ không truy cập được
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //tạo file
        //các tham số là (tên file, định dạng, đường dẫn)
        File photoFile = File.createTempFile(takenDate, ".jpg", storageDir);

        //lưu đường dẫn của file
        photoPath = photoFile.getAbsolutePath();

        return photoFile;
    }

    private void openCam()
    {
        //Khai báo một Intent thực hiện việc gọi một activity chụp ảnh
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // câu lệnh if này để kiểm tra xem hệ thống có Activity nào để chụp ảnh không
        //vì nếu gọi intent mà không có activity nào hoạt động thì ứng dụng sẽ bị crash
        if (intent.resolveActivity(getContext().getPackageManager()) != null )
        {
            //tạo một file trước
            File photoFile;

            try
            {
                //sau khi gọi createPhotoFile, photoPath đã có data đường dẫn
                photoFile = createPhotoFile();

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
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_CHOOSE_PHOTO_FROM_GALLERY:
                {
                    currrentPhoto = convertToBitmap(getContext(), data.getData());
                    ivCategoryAvatar.setImageBitmap(currrentPhoto);
                    break;
                }

                case REQUEST_IMAGE_CAPTURE:
                {
                    currrentPhoto = convertToBitmap(getContext(), photoUri);
                    ivCategoryAvatar.setImageBitmap(currrentPhoto);
                    break;
                }
            }
        }

    }
}
