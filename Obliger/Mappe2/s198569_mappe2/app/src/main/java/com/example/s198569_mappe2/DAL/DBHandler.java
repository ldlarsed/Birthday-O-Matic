package com.example.s198569_mappe2.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.LIB.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 14.10.2015.
 */
public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_BDAYBUDDIES = "BDayBuddies";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Name";
    static String  KEY_PH_NUMBER = "Phone";
    static String B_DATE = "BDate";
    static String M_DATE = "MDate";
    static String MESSAGE = "Message";
    static String IS_ACTIVE = "isActive";
    static int DATABASE_VERSION = 2;
    static String DATABASE_NAME = "BDayOMatic";

    public DBHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DB = "CREATE TABLE " + TABLE_BDAYBUDDIES +
            " ("                +
            KEY_ID              +   " INTEGER PRIMARY KEY,"     +
            KEY_NAME            +   " TEXT,"                    +
            KEY_PH_NUMBER       +   " TEXT,"                    +
            B_DATE              +   " DATETIME,"                +
            M_DATE              +   " DATETIME,"                +
            MESSAGE             +   " TEXT,"                    +
            IS_ACTIVE           +   " INTEGER DEFAULT 0"        +
                ")";

        Log.d(Constants.TAG_SQL, CREATE_DB); //Sendign to logcat

        try{
            db.execSQL(CREATE_DB);
        } catch (SQLException e){
            Log.w(Constants.TAG_SQL, Constants.TABLE_CREATION_PROBLEM);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BDAYBUDDIES);
        onCreate(db);
    }

    public boolean addBuddy(Person buddy){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, buddy.getName());
        values.put(KEY_PH_NUMBER, buddy.getPhoneNumber());
        values.put(B_DATE, buddy.getSimpleBirthdayDate());
        values.put(M_DATE, buddy.getSimpleMessageDate());
        values.put(MESSAGE, buddy.getBirthdayMessage());
        values.put(IS_ACTIVE, buddy.isActive()?1:0);

        try{
            db.insertOrThrow(TABLE_BDAYBUDDIES, null, values);
            db.close();
            return true;
        }catch(SQLException e){
            Log.w(Constants.TAG_SQL, Constants.TABLE_INSERTION_PROBLEM);
            db.close();
            return false;
        }
    }


    /**
     * Returns a list of all registered buddies
     * @return
     */
    public ArrayList<Person> getAllBuddies(){
        ArrayList<Person> buddies = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_BDAYBUDDIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if(cursor.moveToFirst()){
            do {
                Person p = new Person();
                p.set_ID(cursor.getInt(0));
                p.setName(cursor.getString(1));
                p.setPhoneNumber(cursor.getString(2));
                p.setBDateFromDB(cursor.getString(3));
                p.setMDateFromDB(cursor.getString(4));
                p.setBirthdayMessage(cursor.getString(5));
                p.setIsActive((cursor.getInt(6)==1)?true:false);

                buddies.add(p);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return buddies;
    }

    public int getBuddyCount(){
        String sql = "SELECT * FROM " + TABLE_BDAYBUDDIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int c = cursor.getCount();
        cursor.close();
        db.close();
        return c;
    }
}
