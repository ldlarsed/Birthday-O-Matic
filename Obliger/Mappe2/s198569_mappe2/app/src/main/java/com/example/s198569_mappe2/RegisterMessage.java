package com.example.s198569_mappe2;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.DAL.DBHandler;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.LIB.DialogYesNoListener;
import com.example.s198569_mappe2.fragments.InfoDialogFragment;

import java.util.Date;

public class RegisterMessage extends AppCompatActivity implements DialogYesNoListener {

    private TextView buddyName, buddyBDay;
    private EditText messageText;
    //private TimePicker messageTime;
    private Switch isActiveSwitch;
    private Person person, p;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //v7 action bar prevents nullpointer
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_register_message);


        messageText = (EditText) findViewById(R.id.addmessageGreetingsText);
        //messageTime = (TimePicker) findViewById(R.id.addmessageTimePicker);
        //messageTime.setIs24HourView(true);
        isActiveSwitch = (Switch) findViewById(R.id.addmessageSwitchIsActivate);

        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra(Constants.TAG_PERSON);

        //Sets up name and birthdaydate of the newly added buddy i this activity header
        buddyName = (TextView) findViewById(R.id.addmessageHeaderText);
        buddyBDay = (TextView) findViewById(R.id.addmessageBirthdayDateText);
        buddyName.setText(person.getName());
        buddyBDay.setText(person.getSimpleBirthdayDate());

        //If we are editing an existing person we can populate all the textboxes
        if(person.getBirthdayMessage() != null){
            messageText.setText(person.getBirthdayMessage());
            isActiveSwitch.setChecked(person.isActive());
        }

        Log.i(Constants.TAG_PERSON, person.toString());

        db = new DBHandler(this);
        //db.addBuddy(person);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_register_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
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
        //This is set to current registration time since the time picker is not longer used.
        person.setMessageTime(date);

        return person;
    }


    public void saveBuddyAlert(View view){
        if(isValid()){
            p = getInputData();
            Log.i(Constants.TAG_PERSON, p.toString());
            DialogFragment dialog = new InfoDialogFragment();
            dialog.show(getFragmentManager(), "");
        }
    }

    @Override
    public void onYesClick() {
        Log.i(Constants.TAG_LISTENER, Constants.DIALOG_YES_CLICK_REGISTERED);
        db.addBuddy(p);
        this.finish();
    }

    @Override
    public void onNoClick() {
        Log.i(Constants.TAG_LISTENER, Constants.DIALOG_NO_CLICK_REGISTERED);
    }

}
