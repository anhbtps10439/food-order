package com.pro1121.foodorder.activity.UserCase.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro1121.foodorder.R;
import com.pro1121.foodorder.adapter.OrderHistoryRecyclerViewAdapter;
import com.pro1121.foodorder.model.OrderModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistoryFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView rvOrderHistory;
    private OrderHistoryRecyclerViewAdapter adapter;

    private ArrayList<OrderModel> orderHistoryList;
    private ArrayList<Integer> orderHistoryPriceList;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    public OrderHistoryFragment(ArrayList<OrderModel> orderHistoryList, ArrayList<Integer> orderHítoryPriceList) {
        this.orderHistoryList = orderHistoryList;
        this.orderHistoryPriceList = orderHítoryPriceList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        rvOrderHistory = view.findViewById(R.id.rvOrderHistory);
        setAdapter();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_white,null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_user_case, new ProfileFragment()).commit();
            }
        });
    }

    public void setColorToolbarAndStatusBar(Toolbar toolbar){
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorRedMain,null));
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorRedMain,null));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setNavigationIcon(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        setColorToolbarAndStatusBar(toolbar);
    }

    private void setAdapter()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvOrderHistory.setLayoutManager(layoutManager);
        adapter = new OrderHistoryRecyclerViewAdapter(getContext(), orderHistoryList, orderHistoryPriceList);
        rvOrderHistory.setAdapter(adapter);
    }
}
