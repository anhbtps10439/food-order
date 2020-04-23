package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.adapter.DishCategoryAdapter;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.model.DishCategoryModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.pro1121.foodorder.LibraryClass.categoryPicList;
import static com.pro1121.foodorder.LibraryClass.convertToBitmap;
import static com.pro1121.foodorder.LibraryClass.currrentPhoto;
import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;
import static com.pro1121.foodorder.LibraryClass.downloadURL;
import static com.pro1121.foodorder.LibraryClass.photoPath;
import static com.pro1121.foodorder.LibraryClass.photoUpload;
import static com.pro1121.foodorder.LibraryClass.photoUri;


public class CategoryAdminFragment extends Fragment implements DishCategoryAdapter.OnItemClick {

    private Context context;
    private RecyclerView recyclerView;
    private FloatingActionButton fbCategory;
    private Toolbar toolbar;
    private DishCategoryAdapter dishCategoryAdapter;
    //dialog
    private ImageView ivCategoryAvatar;
    private AlertDialog alertDialog;
    private String categoryID, categoryName, categoryDes;
    private DishCategoryDao dao;
    public static final int REQUEST_CHOOSE_PHOTO_FROM_GALLERY = 2;
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public CategoryAdminFragment(Context context) {
        this.context = context;
        this.dao = new DishCategoryDao(context);
    }

    public CategoryAdminFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_dish);
        fbCategory = view.findViewById(R.id.fbCategory);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        dishCategoryAdapter = new DishCategoryAdapter(getActivity(), this);
        recyclerView.setAdapter(dishCategoryAdapter);

        //Floating button
        fbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
                builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_insert_category, null, false));
                builder.setTitle("Thêm loại");
                builder.setPositiveButton("Thêm", null);


                alertDialog = builder.create();
                alertDialog.show();
                Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                final EditText etCategoryCode = alertDialog.findViewById(R.id.etCategoryCodeDialog);
                final EditText etCategoryName = alertDialog.findViewById(R.id.etCategoryNameDialog);
                final EditText etCategoryDes = alertDialog.findViewById(R.id.etCategoryDesDialog);

                //Click to open gallery
//                ivCategoryAvatar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(), "Mở Gallery !!!", Toast.LENGTH_SHORT).show();
//                        //intent mở gallery
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                        intent.setType("image/*");
//                        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO_FROM_GALLERY);
//                    }
//                });
                //Click to open Camera
//                btnOpenCam.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Open Camera
//                        Toast.makeText(getActivity(), "Mở Camera !!!", Toast.LENGTH_SHORT).show();
//                        openCam();
//                    }
//                });

                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            categoryID = etCategoryCode.getText().toString();
                            categoryName = etCategoryName.getText().toString();
                            categoryDes = etCategoryDes.getText().toString();

                            //Check null
                            if (categoryID.equals("") || categoryName.equals("") || categoryDes.equals("")) {
                                Toast.makeText(getActivity(), "Chưa đủ dữ liệu", Toast.LENGTH_SHORT).show();
                            } else if (ivCategoryAvatar.getDrawable() == null) {
                                Toast.makeText(getActivity(), "Chưa có ảnh", Toast.LENGTH_SHORT).show();
                            } else {
//                                downloadURL = photoUpload(getActivity(), currrentPhoto);
                                dao.insert(categoryID, categoryName, categoryDes, "none");
                                dishCategoryModelList.add(new DishCategoryModel(categoryID, categoryName, categoryDes, "none"));
//                                categoryPicList.add(currrentPhoto);
                                dishCategoryAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Lỗi thêm", Toast.LENGTH_SHORT).show();
                        }
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Loại thức ăn");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_category, menu);
        setColorToolbarAndStatusBar(toolbar);
    }
    // Onclick button show all dish in new Fragment
    @Override
    public void onClick(View view, int position) {
        try {
            String id = dishCategoryModelList.get(position).getId();
            String name = dishCategoryModelList.get(position).getName();
            Bundle bundle = new Bundle();
            bundle.putString("nameCategory", name);
            bundle.putString("id", id);
            Fragment fragment = new DishFragment();
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_admin_case, fragment, "dish").commit();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Lỗi lấy id", Toast.LENGTH_SHORT).show();
        }


    }

    // Onclick long Item DishRecycle View
    @Override
    public void onLongClick(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_on_long_click_dish_category, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    //Edit Dish Category
                    case R.id.it_edit_cate_dish:

                        //Show AlertDialog
                        editDishCategory(position);
                        break;

                    case R.id.it_delete_cate_dish:
                        //Delete DishCategory
                        try {
                            if (LibraryClass.dishFilter(dishCategoryModelList.get(position).getId()).size()>0){
                                Toast.makeText(getActivity(), "Ko thể xoá khi bên trong còn quá nhiều món ăn", Toast.LENGTH_SHORT).show();
                            }else {
                                dao.delete(dishCategoryModelList.get(position).getId(), position, dishCategoryModelList.get(position).getImage());
                                dishCategoryAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
                            Log.d("Delete CateDis Error............", ex.toString());
                        }
                        break;

                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void editDishCategory(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_insert_category, null, false));
        builder.setTitle("Thêm loại");
        builder.setPositiveButton("Thêm", null);

        alertDialog = builder.create();
        alertDialog.show();


        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final EditText etCategoryCode = alertDialog.findViewById(R.id.etCategoryCodeDialog);
        final EditText etCategoryName = alertDialog.findViewById(R.id.etCategoryNameDialog);
        final EditText etCategoryDes = alertDialog.findViewById(R.id.etCategoryDesDialog);


        //setData
        etCategoryCode.setText(dishCategoryModelList.get(position).getId());
        etCategoryDes.setText(dishCategoryModelList.get(position).getDes());
        etCategoryName.setText(dishCategoryModelList.get(position).getName());

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Updating!!!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setColorToolbarAndStatusBar(toolbar);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }
}
