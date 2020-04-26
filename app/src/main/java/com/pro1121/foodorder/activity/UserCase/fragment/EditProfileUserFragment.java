package com.pro1121.foodorder.activity.UserCase.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment;
import com.pro1121.foodorder.activity.SignInOut.SignInActivity;
import com.pro1121.foodorder.dao.UserDao;
import com.pro1121.foodorder.model.UserModel;
import  com.pro1121.foodorder.activity.SignInOut.SignInActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.pro1121.foodorder.LibraryClass.convertToBitmap;
import static com.pro1121.foodorder.LibraryClass.currrentPhoto;
import static com.pro1121.foodorder.LibraryClass.downloadURL;
import static com.pro1121.foodorder.LibraryClass.photoPath;
import static com.pro1121.foodorder.LibraryClass.photoUri;
import static com.pro1121.foodorder.LibraryClass.userModelList;
import static com.pro1121.foodorder.LibraryClass.userPicList;
import static com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment.REQUEST_CHOOSE_PHOTO_FROM_GALLERY;
import static com.pro1121.foodorder.activity.AdminCase.fragment.CategoryAdminFragment.REQUEST_IMAGE_CAPTURE;
import static com.pro1121.foodorder.activity.SignInOut.SignInActivity.currentUser;

public class    EditProfileUserFragment extends Fragment {

    static Context context;
    private TextView tv_birth, tv_phone_num, tv_email;
    private TextView tv_edit_profile, tv_edit_image, tv_name;
    private Toolbar toolbar;
    private Button btn_save;
    private UserDao dao;
    private CircleImageView iv_avatar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setColorToolbarAndStatusBar(toolbar);
        View view = inflater.inflate(R.layout.fragment_edit_information,container,false);

        tv_name = view.findViewById(R.id.tv_name);
        tv_birth = view.findViewById(R.id.tv_birth);
        tv_phone_num = view.findViewById(R.id.tv_phone_number);
        tv_email = view.findViewById(R.id.tv_email);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        tv_edit_image = view.findViewById(R.id.tv_edit_image);
        tv_edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCam();
            }
        });
        tv_edit_profile = view.findViewById(R.id.tv_edit_profile);
        tv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });

        btn_save = view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        dao = new UserDao(getActivity());
        UserModel user = currentUser;

        tv_name.setText(user.getName());
        tv_phone_num.setText(user.getId());
        tv_birth.setText(user.getBirthday());
        tv_email.setText(user.getEmail());


        try {
            //dùng picasso để load url của ảnh
            Picasso.get().load(currentUser.getImage()).fit().into(iv_avatar);
        }catch (Exception e){
            //Set ảnh bị lỗi hoặc chưa có ảnh
            iv_avatar.setBackgroundColor(Color.BLACK);
            Toast.makeText(getActivity(), "Chưa có ảnh hoặc lỗi ảnh, Log d to check", Toast.LENGTH_SHORT).show();
            Log.d("Set image errorrrrrrrrrrrrrrrrrrrrrrrrrrrrr", e.toString());
        }
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CHOOSE_PHOTO_FROM_GALLERY);
            }
        });

        return view;
    }

    private void updateUser() {
        try {
            if (currrentPhoto != null) {

                UserModel userModel = new UserModel(currentUser.getId(), tv_name.getText().toString(), tv_birth.getText().toString(),
                        tv_email.getText().toString(), currentUser.getPassword(), currentUser.getRole(), "none");
                LibraryClass.photoUpload(getActivity(), currrentPhoto, null, userModel, "user");
            }else {
                Toast.makeText(getActivity(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "Lỗi cập nhật, check Log d", Toast.LENGTH_SHORT).show();
            Log.d("Update User Errrorrrrrrrrrrrrrrrrrrrrrrrr", e.toString());
        }

    }

    private void editUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_profile_user, null, false);

        final EditText et_name = view.findViewById(R.id.et_name);
        final EditText et_birth = view.findViewById(R.id.et_birth);
        final EditText et_email = view.findViewById(R.id.et_email);

        builder.setTitle("Sửa thông tin")

                //Edit Info User
                .setView(view).setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_name.setText(et_name.getText().toString());
                tv_birth.setText(et_birth.getText().toString());
                tv_email.setText(et_email.getText().toString());
            }
        }).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_user_case, new ProfileFragment()).commit();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setNavigationIcon(null);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_PHOTO_FROM_GALLERY: {
                    currrentPhoto = convertToBitmap(getContext(), data.getData());
                    iv_avatar.setImageBitmap(currrentPhoto);
                    break;
                }

                case REQUEST_IMAGE_CAPTURE: {
                    currrentPhoto = convertToBitmap(getContext(), photoUri);
                    iv_avatar.setImageBitmap(currrentPhoto);
                    break;
                }
            }
        }

    }
}
