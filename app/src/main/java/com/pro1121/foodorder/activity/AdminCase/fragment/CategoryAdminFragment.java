package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.dao.DishCategoryDao;
import com.pro1121.foodorder.dao.DishDao;
import com.pro1121.foodorder.huyTest.Adapter;


public class CategoryAdminFragment extends Fragment {
    RecyclerView recyclerView;
    private FloatingActionButton fbCategory;
    private Toolbar toolbar;
    Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.rv_dish);
        fbCategory = view.findViewById(R.id.fbCategory);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(getActivity(), LibraryClass.dishModelList);
        recyclerView.setAdapter(adapter);

        fbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final LayoutInflater inflater1 = getActivity().getLayoutInflater();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thêm món")
                        .setView(inflater1.inflate(R.layout.dialog_insert_category,null))
                        .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog alertDialog = (AlertDialog) dialog;
                                EditText dialog_et_dishKind = alertDialog.findViewById(R.id.dialog_et_dishKind);
                                EditText dialog_et_dishKindDes = alertDialog.findViewById(R.id.dialog_et_dishKindDes);
                                String name = dialog_et_dishKind.getText()+"";
                                String des = dialog_et_dishKindDes.getText()+"";
                                DishDao dao = new DishDao(getActivity());
                                dao.insert("MTL001",name,100100,des,"");

                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm thành công ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Hủy", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create();
                builder.show();

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

}
