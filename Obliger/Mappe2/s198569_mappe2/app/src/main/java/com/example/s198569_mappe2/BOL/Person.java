package com.example.s198569_mappe2.BOL;

import java.text.SimpleDateFormat;

/**
 * Created by luke on 10/13/15.
 */
public class Person {

    private String name, phoneNumber, message;
    private SimpleDateFormat birthdayDate, messageTime;

    public SimpleDateFormat getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(SimpleDateFormat birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SimpleDateFormat getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(SimpleDateFormat messageTime) {
        this.messageTime = messageTime;
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
}
