package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    List<User> mUsers = new ArrayList<>();
    AppDatabase mDb;

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);

        mDb = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        //Setup Intent service through Alarm manager
        setAlarmManagerForBackGroundService();

        List<User> users = new ArrayList<>(4);

        users.add(new User(101,"Cafe1","SSDEEFFR"));
        users.add(new User(102,"Cafe2","43DEEFFR"));
        users.add(new User(103,"Cafe3","DFSDFSFR"));
        users.add(new User(104,"Cafe1","NMAJJAAS"));
        mDb.userDao().insertAll(users);


        mUsers= mDb.userDao().getAll();
        String text = "";
        for (User user : mUsers ){
            text += user.uid +  ", " + user.cafeName +", "+ user.barcodeValue+"\n";
        }
        tv1.setText(text);
    }

    //
    private void setAlarmManagerForBackGroundService() {
        // Intent service for sending reoort to API service every day at 11:59pm
        Log.i(" Starting in Activity ", "Setting alarm!!");

        //Get calendar instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 56);
        calendar.set(Calendar.SECOND, 0);

        //Instantiate alarmManager
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Set intent to be called for the service class
        Intent alarmIntent = new Intent(this, ScheduledService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        //configure the alarm manager to call intent service in the future time
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
