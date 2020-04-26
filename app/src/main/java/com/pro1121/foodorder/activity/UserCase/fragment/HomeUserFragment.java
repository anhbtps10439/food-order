package com.pro1121.foodorder.activity.UserCase.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.UserCase.UserCaseActivity;
import com.pro1121.foodorder.adapter.CartDishRecyclerViewAdapter;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.dao.DetailOrderDao;
import com.pro1121.foodorder.dao.OrderDao;
import com.pro1121.foodorder.model.DetailOrderModel;
import com.pro1121.foodorder.model.OrderModel;

import java.util.ArrayList;

import static com.pro1121.foodorder.LibraryClass.buyAmountList;
import static com.pro1121.foodorder.LibraryClass.buyList;
import static com.pro1121.foodorder.LibraryClass.createDate;
import static com.pro1121.foodorder.LibraryClass.dishModelList;
import static com.pro1121.foodorder.LibraryClass.orderModelList;
import static com.pro1121.foodorder.activity.SignInOut.SignInActivity.currentUser;

public class HomeUserFragment extends Fragment implements DishAdapter.OnItemClick {
    private Toolbar toolbar;
    private RecyclerView ryc_you_can_like;
    private DishAdapter dish_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        setHasOptionsMenu(true);
        ryc_you_can_like = view.findViewById(R.id.ryc_you_can_like);


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        ryc_you_can_like.setLayoutManager(layoutManager);
        dish_adapter = new DishAdapter(getActivity(), LibraryClass.dishModelList, this);
        ryc_you_can_like.setAdapter(dish_adapter);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_dish_user,menu);
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

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()){
            //Cart
            case R.id.iv_cart:
                addFoodToCart(position);
                break;
            //Detail dish
            case R.id.tv_show:
                UserCaseActivity.position_dish_detail = position;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_user_case, new DetailDishFragment()).commit();
                break;
        }
    }



    private void addFoodToCart(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_dish_info, null, false));

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final Button btnAddToCart = alertDialog.findViewById(R.id.btnAddToCartDialog);
        Button btnPlus = alertDialog.findViewById(R.id.btnPlus);
        final Button btnSub = alertDialog.findViewById(R.id.btnSub);
        final TextView tvAmount = alertDialog.findViewById(R.id.tvAmoumt);
        TextView tvName = alertDialog.findViewById(R.id.tvDishNameDialog);
        TextView tvPrice = alertDialog.findViewById(R.id.tvDishPriceDialog);
        TextView tvDes = alertDialog.findViewById(R.id.tvDishDesDialog);

        tvName.setText(dishModelList.get(position).getName());
        tvPrice.setText(dishModelList.get(position).getPrice() + "");
        tvDes.setText(dishModelList.get(position).getDes());
        btnAddToCart.setEnabled(false); //ban đầu không cho nút hoạt động

        //cộng trừ số lượng

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(tvAmount.getText().toString());
                amount++;
                tvAmount.setText("" + amount);

                //bấm nút cộng thì sẽ enable nút trừ và add
                btnAddToCart.setEnabled(true);
                btnSub.setEnabled(true);
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(tvAmount.getText().toString());
                amount--;
                //nếu amount = 0 thì disable nút add và nút trừ
                if (amount <= 0)
                {
                    tvAmount.setText("" + 0);
                    btnAddToCart.setEnabled(false);
                    btnSub.setEnabled(false);
                }
                else
                {
                    tvAmount.setText(amount+"");
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(tvAmount.getText().toString());
                //nếu amount = 0 thì không làm gì cả

                if (amount > 0)
                {
                    buyList.add(dishModelList.get(position));
                    buyAmountList.add(amount);
                    Toast.makeText(getContext(), "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    Log.e("size", "List: " + buyList.size() + "    Amount size: " + buyAmountList.size());
                }
            }
        });
    }

    @Override
    public void onLongClick(View view, int position) {

    }


    //Nhấn nút xem giỏ hàng
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_cart:

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(LayoutInflater.from(getContext()).inflate(R.layout.dialog_cart, null, false));

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button btnOrder = alertDialog.findViewById(R.id.btnOrder);
                RecyclerView rvCart = alertDialog.findViewById(R.id.rvCart);
                final EditText etDes = alertDialog.findViewById(R.id.etDesCartDialog);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvCart.setLayoutManager(layoutManager);
                CartDishRecyclerViewAdapter adapter = new CartDishRecyclerViewAdapter(buyList, buyAmountList, getContext());
                rvCart.setAdapter(adapter);

                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buyList.size() <= 0)
                        {
                            Toast.makeText(getContext(), "Hãy thêm món ăn vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //đặt đơn
                            //tạo id, ngày tháng, ghi chú
                            String des= etDes.getText().toString();
                            OrderDao orderDao = new OrderDao(getContext());
                            String date = createDate();
                            String id = orderDao.insert(date, currentUser.getId(), des);

                            //thêm các món ăn vào child detailOrder
                            DetailOrderDao detailDao = new DetailOrderDao(getContext());

                            ArrayList<DetailOrderModel> detailOrderList = new ArrayList<>();
                            for (int i = 0; i < buyList.size(); i++)
                            {

                                String detailOrderID = detailDao.insert(id, buyList.get(i), buyAmountList.get(i));
                                detailOrderList.add(new DetailOrderModel(detailOrderID, buyList.get(i), buyAmountList.get(i)));
                            }


                            OrderModel orderModel = new OrderModel(id, date, currentUser.getId(), des);
                            orderModel.setDetailOrderList(detailOrderList);
                            orderModelList.add(orderModel);

                            orderDao.priceCal();

                            alertDialog.dismiss();
                        }

                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
