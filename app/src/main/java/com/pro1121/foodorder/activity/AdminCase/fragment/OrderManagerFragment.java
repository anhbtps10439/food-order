package com.pro1121.foodorder.activity.AdminCase.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.adapter.OrderHistoryRecyclerViewAdapter;

import static com.pro1121.foodorder.LibraryClass.orderModelList;
import static com.pro1121.foodorder.LibraryClass.priceList;

public class OrderManagerFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView rvOrderManagement;
    private OrderHistoryRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_manager,container,false);

        rvOrderManagement = view.findViewById(R.id.rvOrderManagement);
        setAdapter();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarAdminCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Quản lí đơn hàng");
        setColorToolbarAndStatusBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp,null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getFragmentManager().findFragmentByTag("order_manager");
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setColorToolbarAndStatusBar(toolbar);
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar){
        toolbar.setBackgroundColor(Color.WHITE);
        getActivity().getWindow().setStatusBarColor(Color.WHITE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setNavigationIcon(null);
    }

    private void setAdapter()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvOrderManagement.setLayoutManager(layoutManager);
        adapter = new OrderHistoryRecyclerViewAdapter(getContext(), orderModelList, priceList);
        rvOrderManagement.setAdapter(adapter);
    }
}
