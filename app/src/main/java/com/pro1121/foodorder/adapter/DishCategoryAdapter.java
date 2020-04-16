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
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.AdminCaseActivity;
import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.model.DishCategoryModel;

import java.util.List;

public class DishCategoryAdapter extends RecyclerView.Adapter<DishCategoryAdapter.ViewHolder> {

    static Context context;
    private List<DishCategoryModel> categoryModelList;
    private OnItemClick onItemClick;


    public DishCategoryAdapter(Context context,  List<DishCategoryModel> categoryModelLists,OnItemClick onItemClick){
        this.context=context;
        this.categoryModelList= categoryModelLists;
        this.onItemClick = onItemClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private ImageView iv_category_image;
        private TextView name, des;
        private ImageButton ib_show_all_dish;
        private OnItemClick onItemClick;

        public ViewHolder(final View itemView, OnItemClick onItemClick){
            super(itemView);
            iv_category_image = itemView.findViewById(R.id.iv_dish_category);
            name = itemView.findViewById(R.id.tv_dish_category_name);
            ib_show_all_dish = itemView.findViewById(R.id.ib_show_all_dish);
            this.onItemClick = onItemClick;
            ib_show_all_dish.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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

        @Override
        public boolean onLongClick(View v) {
            onItemClick.onLongClick(v,getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.cardview_dishes_category, parent,false);
        return new ViewHolder(view, onItemClick);
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
            holder.iv_category_image.setBackground(new BitmapDrawable(context.getResources(),LibraryClass.categoryPicList.get(position)));
        }catch (Exception e){
            Toast.makeText(context, "Lỗi gán ảnh", Toast.LENGTH_SHORT).show();
            Log.d("Set Image Error >>>>>>>>>>>>>>>>>>", e.toString());
            holder.iv_category_image.setBackgroundColor(Color.parseColor("#ff3737"));
        }
        holder.name.setText(list.getName());
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public interface OnItemClick{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
