package com.example.s198569_mappe2.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.s198569_mappe2.BLL.BirthdayController;
import com.example.s198569_mappe2.BLL.SMSHandler;
import com.example.s198569_mappe2.BOL.Person;

import java.util.ArrayList;

/**
 * Created by luke on 10/19/15.
 */
public class DailyMessageService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("Service", "DailyMessageService is running");

        BirthdayController bDayController = new BirthdayController(getApplicationContext());
        ArrayList<Person> bDayPeople = bDayController.getTodaysBDays();

        if(bDayPeople.size() > 0){
            //Send sms to all people having birthday
            for(Person p : bDayPeople){
                SMSHandler.sendSMS(p.getPhoneNumber(), p.getBirthdayMessage());
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
