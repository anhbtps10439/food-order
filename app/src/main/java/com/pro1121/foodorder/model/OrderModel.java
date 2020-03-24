package com.pro1121.foodorder.model;

import java.util.ArrayList;

public class OrderModel {
    private String id;
    private String orderDate;
    private String userId;
    private String des;
    private ArrayList<DetailOrderModel> detailOrderList;

    public OrderModel()
    {

    }

    public OrderModel(String id, String orderDate, String userId, String des) {
        this.id = id;
        this.orderDate = orderDate;
        this.userId = userId;
        this.des = des;
        this.detailOrderList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public ArrayList<DetailOrderModel> getDetailOrderList() {
        return detailOrderList;
    }

    public void setDetailOrderList(ArrayList<DetailOrderModel> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }
}
