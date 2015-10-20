package com.example.s198569_mappe2.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.s198569_mappe2.BLL.BirthdayController;
import com.example.s198569_mappe2.BLL.SMSHandler;
import com.example.s198569_mappe2.BOL.Person;

import java.util.ArrayList;
import java.util.EmptyStackException;

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
        try {
            ArrayList<Person> bDayPeople = bDayController.getTodaysBDays();

            if(bDayPeople.size() > 0){
                //Send sms to all people having birthday
                for(Person p : bDayPeople){
                    Log.i("Service", "Sending message to: " + p.getName());
                    SMSHandler.sendSMS(p.getPhoneNumber(), p.getBirthdayMessage());
                }
            }

        }catch (EmptyStackException e){
            Toast.makeText(getApplicationContext(), "No contacts to show", Toast.LENGTH_SHORT).show();
        }


        return super.onStartCommand(intent, flags, startId);
    }
}
