package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.adapter.DishCategoryAdapter;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.model.DishModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.pro1121.foodorder.LibraryClass.convertToBitmap;
import static com.pro1121.foodorder.LibraryClass.currrentPhoto;
import static com.pro1121.foodorder.LibraryClass.dishModelList;
import static com.pro1121.foodorder.LibraryClass.dishPicList;
import static com.pro1121.foodorder.LibraryClass.downloadURL;
import static com.pro1121.foodorder.LibraryClass.photoPath;
import static com.pro1121.foodorder.LibraryClass.photoUpload;
import static com.pro1121.foodorder.LibraryClass.photoUri;
import static com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment.REQUEST_CHOOSE_PHOTO_FROM_GALLERY;
import static com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment.REQUEST_IMAGE_CAPTURE;

public class DishFragment extends Fragment implements DishAdapter.OnItemClick {

    static Context context;
    private Toolbar toolbar;
    private RecyclerView ryc_dish;
    private AlertDialog alertDialog;
    private ImageView ivCategoryAvatar;
    private String mDishPrice, mDishName, mDishDes, nameCategory,idCategory; //id category; //nameCategory
    private TextView tv_set_dish_category_name;
    private DishDao dishDao;
    private FloatingActionButton flb_add_dish;
    private DishAdapter dishAdapter;
    private ArrayList<DishModel> dishList;
    private ArrayList<Bitmap> picList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish, container, false);

        dishDao = new DishDao(getActivity());

        tv_set_dish_category_name = view.findViewById(R.id.tv_set_dish_category_name);
        ryc_dish = view.findViewById(R.id.ryc_dish);
        flb_add_dish = view.findViewById(R.id.flb_add_dish);

        tv_set_dish_category_name.setText(nameCategory + "");

        //============= Lọc list by id
        try {
            dishList = LibraryClass.dishFilter(idCategory);
//            picList = LibraryClass.dishPicFilter(idCategory);
        }catch (Exception e){

        }
        //=============

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        ryc_dish.setLayoutManager(layoutManager);
        dishAdapter = new DishAdapter(getActivity(), dishList, picList, this);
        ryc_dish.setAdapter(dishAdapter);

        flb_add_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDish();
            }
        });

        return view;
    }

    private void addDish() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        View view = getLayoutInflater().inflate(R.layout.dialog_insert_dish, null, false);

        ivCategoryAvatar = view.findViewById(R.id.ivAvatarDialog);
        Button btnOpenCam = view.findViewById(R.id.btnOpenCamDialog);
        final EditText etDishName = view.findViewById(R.id.etNameDialog);
        final EditText etDishDes = view.findViewById(R.id.etDesDialog);
        final EditText etDishPrice = view.findViewById(R.id.etPriceDialog);

        builder.setView(view)
                .setTitle("Thêm loại")

                //button add
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mDishName = etDishName.getText().toString();
                        mDishDes = etDishDes.getText().toString();
                        mDishPrice = etDishPrice.getText().toString();

                        try {
                            if (mDishName.equals("") || mDishDes.equals("") || mDishPrice.equals("")) {
                                Toast.makeText(getActivity(), "Chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                            }
                            else {
//                                downloadURL = photoUpload(getActivity(), currrentPhoto);
//                                insert vào db và add vào các list hiện có
                                String id = dishDao.insert(idCategory, mDishName, Integer.parseInt(mDishPrice), mDishDes, "none");
                                dishModelList.add(new DishModel(id, idCategory, mDishName, Integer.parseInt(mDishPrice), mDishDes, "none"));
                                dishList.add(new DishModel(id, idCategory, mDishName, Integer.parseInt(mDishPrice), mDishDes, "none"));
//                                dishPicList.add(currrentPhoto);
//                                picList.add(currrentPhoto);
                                dishAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Lỗi thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Click to open gallery
//        ivCategoryAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Mở Gallery !!!", Toast.LENGTH_SHORT).show();
//                //intent mở gallery
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_CHOOSE_PHOTO_FROM_GALLERY);
//            }
//        });
        //Click to open Camera
//        btnOpenCam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Open Camera
//                Toast.makeText(getActivity(), "Mở Camera !!!", Toast.LENGTH_SHORT).show();
//                openCam();
//            }
//        });
        builder.show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarAdminCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishList.clear();
                ryc_dish.setAdapter(null);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_admin_case, new CategoryAdminFragment()).commit();

            }
        });

        idCategory = getArguments().getString("id");
        nameCategory = getArguments().getString("nameCategory");
        Log.d("Check id + name category", idCategory + nameCategory);
    }


    //Onclick item == ko dùng
    @Override
    public void onClick(View view, int position) {

    }

    //OnLongClick item to edit
    @Override
    public void onLongClick(View view, int position) {

    }

    //hàm lưu file ảnh, đặt tên bằng ngày giờ chụp
    //File này chưa có data ảnh, chỉ có tên, đường dẫn
    //hàm này sẽ được gọi để tạo ra một file chứa thống tin trên, sau đó để vào Uri rồi cho vào intent
    private File createPhotoFile() throws IOException {
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

    private void openCam() {
        //Khai báo một Intent thực hiện việc gọi một activity chụp ảnh
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // câu lệnh if này để kiểm tra xem hệ thống có Activity nào để chụp ảnh không
        //vì nếu gọi intent mà không có activity nào hoạt động thì ứng dụng sẽ bị crash
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            //tạo một file trước
            File photoFile;

            try {
                //sau khi gọi createPhotoFile, photoPath đã có data đường dẫn
                photoFile = createPhotoFile();

                //nếu photoFile đã được tạo thành công
                if (photoFile != null) {
                    //gán Uri cho currentPhotoUri
                    photoUri = FileProvider.getUriForFile(context, "com.example.android.fileprovider", photoFile);

                    //đặt Uri vào intent
                    //vì action của intent là ACTION_IMAGE_CAPTURE
                    //nên cái extra có key EXTRA_OUTPUT này sẽ giúp camera lưu hình ảnh vào currentPhotoUri
                    //và Intent data ở onActivityResult sẽ null, vì file ảnh đã được lưu trong path rồi

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_category, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setNavigationIcon(null);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_PHOTO_FROM_GALLERY: {
                    currrentPhoto = convertToBitmap(getContext(), data.getData());
                    ivCategoryAvatar.setImageBitmap(currrentPhoto);
                    break;
                }

                case REQUEST_IMAGE_CAPTURE: {
                    currrentPhoto = convertToBitmap(getContext(), photoUri);
                    ivCategoryAvatar.setImageBitmap(currrentPhoto);
                    break;
                }
            }
        }

    }

}
