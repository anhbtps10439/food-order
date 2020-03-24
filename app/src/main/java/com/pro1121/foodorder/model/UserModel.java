package com.pro1121.foodorder.model;

public class UserModel {

    private String id; //phone number
    private String name;
    private String birthday; //dd/MM/yyyy
    private String email;
    private String password;
    private String role;

    public UserModel()
    {

    }

    public UserModel(String name, String birthday, String id, String email, String password, String role) {
        this.name = name;
        this.birthday = birthday;
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
