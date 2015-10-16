package com.example.s198569_mappe2.BLL;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.DAL.DBHandler;
import com.example.s198569_mappe2.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;

/**
 * Created by luke on 10/16/15.
 */
public class BirthdayController  extends AppCompatActivity {

    private static DBHandler db;

    public BirthdayController() {
        db = new DBHandler(getApplicationContext());
    }

    /**
     * Provides an ArrayList with collection of buddies having bday today.
     * @param cal
     * @return
     */
    public static ArrayList<Person> getTodaysBDays(Calendar cal) {
        ArrayList<Person> allBuddies = db.getAllBuddies();
        if(allBuddies.size()==0)
            throw new EmptyStackException();

        ArrayList<Person> todaysBDays = new ArrayList<>();
        Date today = new Date(System.currentTimeMillis());

        for(Person p : allBuddies){
            if(p.getBirthdayDate().equals(today)){
                todaysBDays.add(p);
            }
        }
        return todaysBDays;
    }
}
