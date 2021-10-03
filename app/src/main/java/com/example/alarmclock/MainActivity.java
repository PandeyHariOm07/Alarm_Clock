package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TimePicker alarmTimePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void OnToggleClicked(View view) {
        long time;
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(this, "ALARM ON", Toast.LENGTH_LONG).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReciever.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            time = (calendar.getTimeInMillis() - calendar.getTimeInMillis() % 60000);
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (60 * 60 * 1000 * 12);
                else
                    time = time + (60 * 60 * 1000 * 24);
                }
//            RTC_WAKEUP—Wakes up the device to fire the pending intent at the specified time.
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,0,pendingIntent);
            }
        else{
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this,"ALARM OFF",Toast.LENGTH_LONG).show();
        }
        }
    }