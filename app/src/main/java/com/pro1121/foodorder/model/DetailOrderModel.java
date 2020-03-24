package com.pro1121.foodorder.model;

public class DetailOrderModel {

    private String id;
    private DishModel dish;
    private int amount;

    public DetailOrderModel()
    {

    }

    public DetailOrderModel(String id, DishModel dish, int amount) {
        this.id = id;
        this.dish = dish;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DishModel getDish() {
        return dish;
    }

    public void setDish(DishModel dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
