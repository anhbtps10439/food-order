package com.pro1121.foodorder.activity.UserCase.fragment;

import android.graphics.Bitmap;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.activity.UserCase.UserCaseActivity;
import com.pro1121.foodorder.adapter.CartDishRecyclerViewAdapter;
import com.pro1121.foodorder.adapter.DishAdapter;
import com.pro1121.foodorder.dao.DetailOrderDao;
import com.pro1121.foodorder.dao.OrderDao;
import com.pro1121.foodorder.model.DishModel;

import java.util.ArrayList;

import static com.pro1121.foodorder.LibraryClass.buyAmountList;
import static com.pro1121.foodorder.LibraryClass.buyList;
import static com.pro1121.foodorder.LibraryClass.createDate;
import static com.pro1121.foodorder.LibraryClass.createDateForFileName;
import static com.pro1121.foodorder.activity.SignInOut.SignInActivity.currentUser;

public class DishUserFragment extends Fragment implements DishAdapter.OnItemClick{
    private Toolbar toolbar;
    private TextView tv_set_dish_category_name;
    private RecyclerView ryc_dish;
    private FloatingActionButton flb_add;
    private String idCategory, nameCategory;
    private ArrayList<DishModel> dishList;
    private ArrayList<Bitmap> picList;
    private DishAdapter dishAdapter;
    static int position;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setColorToolbarAndStatusBar(toolbar);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_dish,container,false);

        tv_set_dish_category_name = view.findViewById(R.id.tv_set_dish_category_name);
        tv_set_dish_category_name.setText(LibraryClass.dishCategoryModelList.get(position).getName());
        ryc_dish = view.findViewById(R.id.ryc_dish);
        flb_add = view.findViewById(R.id.flb_add_dish);
        flb_add.hide();

        dishList = LibraryClass.dishFilter(LibraryClass.dishCategoryModelList.get(position).getId());
//        picList = LibraryClass.dishPicFilter(idCategory);

        //=============

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        ryc_dish.setLayoutManager(layoutManager);
        dishAdapter = new DishAdapter(getActivity(), dishList, this);
        ryc_dish.setAdapter(dishAdapter);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbarUserCase);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setColorToolbarAndStatusBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_user_case, new CategoryUserFragment()).commit();

            }
        });
        position = UserCaseActivity.positon_dish;
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

    //Icon on dish clicked
    @Override
    public void onClick(View view, final int position) {
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

        tvName.setText(dishList.get(position).getName());
        tvPrice.setText(dishList.get(position).getPrice() + "");
        tvDes.setText(dishList.get(position).getDes());
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
                    buyList.add(dishList.get(position));
                    buyAmountList.add(amount);
                    Toast.makeText(getContext(), "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    Log.e("size", "List: " + buyList.size() + "    Amount size: " + buyAmountList.size());
                }
            }
        });
        }

    //Ko dùng
    @Override
    public void onLongClick(View view, int position) {
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_dish_user, menu);
    }


    //Nhấn nút xem giỏ hàng
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_cart:
                Toast.makeText(getActivity(), "Cart Clicked!!", Toast.LENGTH_SHORT).show();

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
                            String des= etDes.getText().toString();
                            OrderDao dao = new OrderDao(getContext());
                            String id = dao.insert(createDate(), currentUser.getId(), des);
                            DetailOrderDao detailDao = new DetailOrderDao(getContext());
                            for (int i = 0; i < buyList.size(); i++)
                            {
                                detailDao.insert(id, buyList.get(i), buyAmountList.get(i));
                            }
                            alertDialog.dismiss();
                        }

                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
