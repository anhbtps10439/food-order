package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.adapter.DishCategoryAdapter;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.huyTest.Adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.pro1121.foodorder.LibraryClass.convertToBitmap;
import static com.pro1121.foodorder.LibraryClass.convertToBytes;
import static com.pro1121.foodorder.LibraryClass.openCam;
import static com.pro1121.foodorder.LibraryClass.photoUpload;


public class CategoryAdminFragment extends Fragment {

    private Context context;

    RecyclerView recyclerView;
    private FloatingActionButton fbCategory;
    private Toolbar toolbar;
    Adapter adapter;
    private DishCategoryAdapter dishCategoryAdapter;

    //chụp ảnh và gallery
    private Bitmap currrentPhoto;
    private String photoPath;
    private Uri photoUri;

    //dialog
    private ImageView ivCategoryAvatar;

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

        dishCategoryAdapter = new DishCategoryAdapter(getActivity(), LibraryClass.dishCategoryModelList);
        recyclerView.setAdapter(dishCategoryAdapter);

        //Floating button
        fbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
                builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_insert_category, null, false));
                builder.setTitle("Thêm loại");
                builder.setPositiveButton("Thêm", null);
                
                
                final AlertDialog alertDialog = builder.create();
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
                        openCam(getContext(), photoUri, REQUEST_IMAGE_CAPTURE, getActivity(), photoPath);
                    }
                });
                
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Nút thêm!", Toast.LENGTH_SHORT).show();

                        //Check empty
                        if (etCategoryCode.getText().equals("") ||
                                etCategoryName.getText().equals("") ||
                                etCategoryDes.getText().equals("")){
                            Toast.makeText(getActivity(), "Chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else {
                            try {
                                DishCategoryDao dao = new DishCategoryDao(getContext());
                                dao.insert(etCategoryCode.getText().toString(),
                                        etCategoryName.getText().toString(),
                                        etCategoryDes.getText().toString(),
                                        photoUpload(getContext(), convertToBytes(currrentPhoto)));

                                dishCategoryAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Toast.makeText(getActivity(), "Lỗi Thêm. Vào log d check !!!", Toast.LENGTH_SHORT).show();
                                Log.d("Insert Error >>>>>>>>>>>>>>>>>>>>>>>", e.toString());
                            }
                        }


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
        setColorToolbarAndStatusBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_category,menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_CHOOSE_PHOTO_FROM_GALLERY:
                {
                    currrentPhoto = convertToBitmap(getContext(), data.getData());
                    ivCategoryAvatar.setImageBitmap(currrentPhoto);
                }

                case REQUEST_IMAGE_CAPTURE:
                {
                    currrentPhoto = convertToBitmap(getContext(), photoUri);
                    ivCategoryAvatar.setImageBitmap(currrentPhoto);
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("Loại thức ăn");
        setColorToolbarAndStatusBar(toolbar);
    }
    public void setColorToolbarAndStatusBar(Toolbar toolbar){
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }
}
