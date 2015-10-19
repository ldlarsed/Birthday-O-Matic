package com.example.s198569_mappe2.BLL;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * This is helper class to be used where there is need for getting information about the
 * current persons having bdays.
 */
public class BirthdayController  extends AppCompatActivity {

    private Context context;
    private DBHandler db;

    public BirthdayController(Context c) {
        this.context = c;
        db = new DBHandler(context);
    }

    /**
     * Return an array with values for current day and month
     * @return
     */
    private int[] currentDayMonth(){
        int[] dm = new int[2];
        Calendar cal = Calendar.getInstance();
        dm[0] = cal.get(Calendar.DAY_OF_MONTH);
        dm[1] = cal.get(Calendar.MONTH);
        return dm;
    }

    /**
     * Extracts date and month int to place integer array from a Date object.
     * @param date
     * @return
     */
    private int[] extractDayAndMonth(Date date){
        int[] dm = new int[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        dm[0] = cal.get(Calendar.DAY_OF_MONTH);
        dm[1] = cal.get(Calendar.MONTH);
        return dm;
    }

    /**
     * Provides an ArrayList with collection of buddies having bday today.
     * To be used with a message service to discover to whom send the message.
     * @param
     * @return
     */
    public ArrayList<Person> getTodaysBDays() {
        ArrayList<Person> allBuddies = db.getAllBuddies();
        if(allBuddies.size()==0)
            throw new EmptyStackException();

        ArrayList<Person> todaysBDays = new ArrayList<>();
        Date today = new Date(System.currentTimeMillis());
        int[] t = currentDayMonth();

        for(Person p : allBuddies){
           int[] e = extractDayAndMonth(p.getBirthdayDate());

            if(t[0] == e[0] && t[1] == e[1]){
                todaysBDays.add(p);
            }
        }


        return todaysBDays;
    }
}
