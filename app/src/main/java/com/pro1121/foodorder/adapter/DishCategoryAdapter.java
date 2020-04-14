package com.pro1121.foodorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.AdminCaseActivity;
import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.model.DishCategoryModel;

import java.util.List;

import static com.pro1121.foodorder.activity.SignInOut.MainActivity.category_dish_img;
import static com.pro1121.foodorder.activity.SignInOut.MainActivity.dishs_img;

public class DishCategoryAdapter extends RecyclerView.Adapter<DishCategoryAdapter.ViewHolder> {

    static Context context;
    List<DishCategoryModel> categoryModelList;

    public DishCategoryAdapter(Context context,  List<DishCategoryModel> categoryModelLists){
        this.context=context;
        this.categoryModelList= categoryModelLists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_category;
        public TextView tv_category_name, tv_category_des;
        public ImageButton ib_show_all_dish;

        public ViewHolder(final View itemView){
            super(itemView);
            iv_category = itemView.findViewById(R.id.iv_dish_category);
            tv_category_name = itemView.findViewById(R.id.tv_dish_category_name);
            ib_show_all_dish = itemView.findViewById(R.id.ib_show_all_dish);
            ib_show_all_dish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked!!!", Toast.LENGTH_SHORT).show();
                    ((AdminCaseActivity)v.getContext()).getSupportFragmentManager().beginTransaction()
                            .add(R.id.nav_host_admin_case, new DishFragment(),"dish")
                            .commit();
                }
            });
        }
        void bindData() {
            RecyclerView.LayoutParams params =(RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (getAdapterPosition()%2 == 0){
                params.setMargins(50,50,25,0);
            }else {
                params.setMargins(25,50,50,0);

            }
            itemView.setLayoutParams(params);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.cardview_dishes_category, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishCategoryModel list = categoryModelList.get(position);
        try{
        /*category_dish_img.clear();
        for (int i = 0; i< LibraryClass.categoryPicList.size(); i++){
            category_dish_img.add(new BitmapDrawable(context.getResources(), LibraryClass.categoryPicList.get(position)));
        }*/
        // Duyệt từng index của mảng chứa Bitmap sau đó parse qua Drawable để làm background
            holder.iv_category.setBackground(new BitmapDrawable(context.getResources(),LibraryClass.categoryPicList.get(position)));
        }catch (Exception e){
            Toast.makeText(context, "Lỗi gán ảnh", Toast.LENGTH_SHORT).show();
            Log.d("Set Image Error >>>>>>>>>>>>>>>>>>", e.toString());
            holder.iv_category.setBackgroundColor(Color.parseColor("#ff3737"));
        }


        holder.tv_category_name.setText(list.getName());
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
}
