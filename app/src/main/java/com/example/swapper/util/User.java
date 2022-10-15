package com.example.swapper.util;

public class User {

    private int user_id,points,age;
    private String name,email,sex,phone,address;
    private boolean ban_post,ban_account;

    /*
    private String name, email;
    private int points;
    private boolean ban_post, ban_account;
*/

    public User(String name, String email, int points, boolean ban_post, boolean ban_account){
        this.name=name;
        this.email=email;
        this.points=points;
        this.ban_post=ban_post;
        this.ban_account=ban_account;
    }

    public User(int user_id,int age,String name, String sex,String phone,String address,String email, int points, boolean ban_post, boolean ban_account){
        this.user_id=user_id;
        this.age=age;
        this.name=name;
        this.email=email;
        this.points=points;
        this.ban_post=ban_post;
        this.ban_account=ban_account;
        this.sex=sex;
        this.phone=phone;
        this.address=address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBan_post() {
        return ban_post;
    }

    public void setBan_post(boolean ban_post) {
        this.ban_post = ban_post;
    }

    public boolean isBan_account() {
        return ban_account;
    }

    public void setBan_account(boolean ban_account) {
        this.ban_account = ban_account;
    }








}

