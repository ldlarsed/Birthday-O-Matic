package com.example.s198569_mappe2.BOL;

import android.util.Log;

import com.example.s198569_mappe2.LIB.Constants;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by luke on 10/13/15.
 */
public class Person implements Serializable {

    private int _ID;
    private String name, phoneNumber, birthdayMessage;
    private Date birthdayDate, messageTime;
    private boolean isActive;

    public Person(){

    }

    public Person(String name, String phoneNumber, Date birthdayDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthdayDate = birthdayDate;
    }

    public Person(String name, String phoneNumber, String birthdayMessage, Date birthdayDate, Date messageTime, boolean isActive) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthdayMessage = birthdayMessage;
        this.birthdayDate = birthdayDate;
        this.messageTime = messageTime;
        this.isActive = isActive;
    }

    public Person(int _ID, String name, String phoneNumber, String birthdayMessage, Date birthdayDate, Date messageTime, boolean isActive) {
        this._ID = _ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthdayMessage = birthdayMessage;
        this.birthdayDate = birthdayDate;
        this.messageTime = messageTime;
        this.isActive = isActive;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthdayMessage() {
        return birthdayMessage;
    }

    public void setBirthdayMessage(String birthdayMessage) {
        this.birthdayMessage = birthdayMessage;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Person{" +
                "_ID=" + _ID +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthdayMessage='" + birthdayMessage + '\'' +
                ", birthdayDate=" + birthdayDate +
                ", messageTime=" + messageTime +
                ", isActive=" + isActive +
                '}';
    }

    /**
     * To be used for date only representation in views.
     * Returns a string representation of a date with year-month-day format.
     * @return String
     */
    public String getSimpleYearMonthDay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(birthdayDate);
    }

    /**
     * Class helper function
     * @param date
     * @return
     */
    private String getSimpleDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Returns a simple representation of buddy's birthday date
     * @return
     */
    public String getSimpleBirthdayDate(){
        return getSimpleDate(birthdayDate);
    }


    /**
     * Returns a simple representation of the BDay message date and time
     * @return
     */
    public String getSimpleMessageDate(){
        return getSimpleDate(messageTime);
    }

    /**
     * Internal helper function to recreate date in the same format it was stored in databse
     * @param dateString
     * @param classDate
     */
    private void setDateFromSimpleDate(String dateString, Date classDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            classDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.w(Constants.PERSON, Constants.ERROR_WHILE_PARSING_DATE);
        }
    }

    /**
     * Recreates the date back to the
     * @param
     */
    public void setBDateFromDB(String dateString){
        //setDateFromSimpleDate(date, birthdayDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            birthdayDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.w(Constants.PERSON, Constants.ERROR_WHILE_PARSING_DATE);
        }
    }

    public void setMDateFromDB(String dateString){
        //setDateFromSimpleDate(date, messageTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            messageTime = dateFormat.parse(dateString);
        } catch (ParseException e) {
            Log.w(Constants.PERSON, Constants.ERROR_WHILE_PARSING_DATE);
        }
    }

    private Calendar getCurrentCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBirthdayDate());
        return cal;
    }

    public int getBDayDay(){
        return getCurrentCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public int getBDayMonth(){
        return getCurrentCalendar().get(Calendar.MONTH)+1;
    }

    public int getBDayYear(){
        return getCurrentCalendar().get(Calendar.YEAR);
    }


}
