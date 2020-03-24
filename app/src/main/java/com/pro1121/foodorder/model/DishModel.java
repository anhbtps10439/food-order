package com.pro1121.foodorder.model;

public class DishModel {
    private String id;
    private String dishCategoryId;
    private String name;
    private int price;
    private String des;
    private String image; //temporary

    public DishModel()
    {

    }

    public DishModel(String id, String dishCategoryId, String name, int price, String des, String image) {
        this.id = id;
        this.dishCategoryId = dishCategoryId;
        this.name = name;
        this.price = price;
        this.des = des;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishCategoryId() {
        return dishCategoryId;
    }

    public void setDishCategoryId(String dishCategoryId) {
        this.dishCategoryId = dishCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
