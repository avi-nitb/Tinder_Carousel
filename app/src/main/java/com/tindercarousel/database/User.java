package com.tindercarousel.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "dob")
    public String dob;

    @ColumnInfo(name = "picture")
    public String picture;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "username")
    public String username;

    public User(int id, String name, String address, String dob, String picture, String phone, String username){
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.picture = picture;
        this.phone = phone;
        this.username = username;

    }

    @Ignore
    public User(String name, String address, String dob, String picture, String phone, String username){
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.picture = picture;
        this.phone = phone;
        this.username = username;

    }

}
