package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.room.Room;

public class ScheduledService extends IntentService {

    AppDatabase mDb;

    public ScheduledService() {
        super("ScheduledService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mDb = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        Log.d("Service", "Running service");
        List<User> mUser = mDb.userDao().getAll();
        Gson gson = new Gson();
        for(User user :mUser){
            String result = gson.toJson(user,User.class);
            Log.d("Service", result);

        }


    }
}