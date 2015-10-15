package com.example.s198569_mappe2;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.DAL.DBHandler;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.LIB.DialogYesNoListener;
import com.example.s198569_mappe2.fragments.InfoDialogFragment;

import java.util.Date;

public class RegisterMessage extends AppCompatActivity implements DialogYesNoListener {

    private EditText messageText;
    private TimePicker messageTime;
    private Switch isActiveSwitch;
    private Person person;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_message);


        messageText = (EditText) findViewById(R.id.addmessageGreetingsText);
        messageTime = (TimePicker) findViewById(R.id.addmessageTimePicker);
        messageTime.setIs24HourView(true);
        isActiveSwitch = (Switch) findViewById(R.id.addmessageSwitchIsActivate);

        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra(Constants.TAG_PERSON);
        Log.i(Constants.TAG_PERSON, person.toString());

        db = new DBHandler(this);
        //db.addBuddy(person);
    }

    private boolean isValid(){
        boolean messageOK = messageText.getText().length()!=0;
        if(!messageOK){
            messageText.setError(getString(R.string.regex_empty_message));
        }
        return messageOK;
    }

    private Person getInputData(){
        String message = messageText.getText().toString();
        boolean isActive = isActiveSwitch.isChecked();
        Date date = new Date(System.currentTimeMillis());

        person.setBirthdayMessage(message);
        person.setIsActive(isActive);
        person.setMessageTime(date);

        return person;
    }


    public void saveBuddyAlert(View view){
        if(isValid()){
            Person p = getInputData();
            Log.i(Constants.TAG_PERSON, p.toString());
            //db.addBuddy(p);
            DialogFragment dialog = new InfoDialogFragment();
            dialog.show(getFragmentManager(), "New buddy");
        }
    }

    @Override
    public void onYesClick() {
        Log.i("Listener", "Yes click registered");
    }

    @Override
    public void onNoClick() {
        Log.i("Listener", "No click registered");
    }

}
