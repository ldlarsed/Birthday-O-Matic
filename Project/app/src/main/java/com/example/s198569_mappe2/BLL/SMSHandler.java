package com.example.s198569_mappe2.BLL;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by luke on 10/19/15.
 */
public class SMSHandler {


    public static void sendSMS(String phoneNumber, String message){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("SMS", "SMS message send");
        }catch(Exception e){
            Log.e("SMS", "SMS message error");
        }
    }

}
