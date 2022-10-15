package com.example.swapper.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UsersEntity {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name="user_id")
    private int id;

    @ColumnInfo(name="user_name")
    private String name;

    @ColumnInfo(name="user_email")
    private String email;

    @ColumnInfo(name="user_sex")
    private String sex;

    @ColumnInfo(name="user_age")
    private int age;

    @ColumnInfo(name="user_phone")
    private String phone;

    @ColumnInfo(name="user_address")
    private String address;

    @ColumnInfo(name="ban_post")
    private int ban_post;

    @ColumnInfo(name="ban_account")
    private int ban_account;

    @ColumnInfo(name="user_point")
    private int point;

    public UsersEntity(String name, String email, String sex, int age,String phone, String address) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBan_post() {
        return ban_post;
    }

    public void setBan_post(int ban_post) {
        this.ban_post = ban_post;
    }

    public int getBan_account() {
        return ban_account;
    }

    public void setBan_account(int ban_account) {
        this.ban_account = ban_account;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
