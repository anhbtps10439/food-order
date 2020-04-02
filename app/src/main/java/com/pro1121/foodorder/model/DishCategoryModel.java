package com.pro1121.foodorder.model;

public class DishCategoryModel {

    private String id;
    private String name;
    private String des;
    private String image;

    public DishCategoryModel()
    {

    }

    public DishCategoryModel(String id, String name, String des, String image) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
