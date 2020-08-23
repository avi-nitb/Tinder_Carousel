package com.tindercarousel.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.tindercarousel.MainActivity;
import com.tindercarousel.database.DatabaseHelper;
import com.tindercarousel.database.User;
import com.tindercarousel.database.UserDAO;

import java.lang.ref.WeakReference;

public final class DbTask extends AsyncTask {

    private WeakReference<Activity> activityWeakReference;
    private User user;
    private Context context;

    public DbTask(Activity activity, User user, Context context){
        activityWeakReference = new WeakReference<>(activity);
        this.user = user;
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        UserDAO userDAO = DatabaseHelper.getInstance(context).userDAO();
        userDAO.insertUser(user);
        return true;
    }
}
