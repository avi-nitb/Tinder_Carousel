package com.tindercarousel.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.jvm.Synchronized;

@Database(entities = User.class, exportSchema = false, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "favorites";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DB_NAME).
                    fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}
