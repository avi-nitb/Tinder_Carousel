package com.tindercarousel.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tindercarousel.pojo.FavoritesModel;
import com.tindercarousel.pojo.UserData;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("Select * from user")
    List<FavoritesModel> getUsers();

    @Insert
    void insertUser(User user);
}
