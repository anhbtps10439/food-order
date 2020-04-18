package com.pro1121.foodorder.activity.UserCase.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.AdminCase.fragment.DishFragment;
import com.pro1121.foodorder.adapter.DishCategoryAdapter;

import static com.pro1121.foodorder.LibraryClass.dishCategoryModelList;

public class CategoryUserFragment extends Fragment implements DishCategoryAdapter.OnItemClick {
    private Toolbar toolbar;
    private FloatingActionButton fbCategory;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container,false);
        recyclerView = view.findViewById(R.id.rv_dish);
        fbCategory = view.findViewById(R.id.fbCategory);
        fbCategory.hide();
        setHasOptionsMenu(true);
        setColorToolbarAndStatusBar(toolbar);


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        DishCategoryAdapter dishCategoryAdapter = new DishCategoryAdapter(getContext(), this);
        recyclerView.setAdapter(dishCategoryAdapter);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Loại món ăn");

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_category,menu);
    }

    @Override
    public void onClick(View view, int position) {
        try {
            String id = dishCategoryModelList.get(position).getId();
            String name = dishCategoryModelList.get(position).getName();
            Bundle bundle = new Bundle();
            bundle.putString("nameCategory", name);
            bundle.putString("id", id);
            Fragment fragment = new DishUserFragment();
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_user_case, fragment).commit();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Lỗi lấy id", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
    }
}