package com.example.s198569_mappe2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.LIB.Constants;

import java.util.Date;

public class RegisterPerson extends AppCompatActivity {

    private EditText nameText, phoneText;
    private DatePicker bDate;
    private Person buddyToEdit;
    private boolean isEditSession = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); //v7 action bar prevents nullpointer
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_register_person);

        nameText = (EditText) findViewById(R.id.addnewNameEdit);
        phoneText = (EditText) findViewById(R.id.addnewPhoneEdit);
        bDate = (DatePicker) findViewById(R.id.addnewDatePicker);
        bDate.setMaxDate(System.currentTimeMillis()); //Today is the max date that can be set
        long MILLS_IN_YEAR = 1000L * 60 * 60 * 24 * 365;
        bDate.setMinDate(System.currentTimeMillis() - (MILLS_IN_YEAR * 100)); //For now we stop at 100 years in the past

        /*
         This happens only if we receive an person object to edit.
         If we are registering a new person this will be null therefore
         all wont be populated.
         */
        Intent i = getIntent();
        isEditSession = i.getBooleanExtra(Constants.IS_EDIT_SESSION, false);
        if(isEditSession){
            buddyToEdit = (Person) i.getSerializableExtra(Constants.TAG_PERSON);
            nameText.setText(buddyToEdit.getName());
            phoneText.setText(buddyToEdit.getPhoneNumber());
            bDate.updateDate(buddyToEdit.getBDayYear(), buddyToEdit.getBDayMonth(), buddyToEdit.getBDayDay());
        }


        phoneText.setRawInputType(Configuration.KEYBOARD_12KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_person, menu);
        //menu.getItem(0).setEnabled(false); //Disables the add new button
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.register_person_next:
                addMessage(getCurrentFocus());
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean isValid(){
        boolean nameOK = false;
        boolean phoneOK = false;
        boolean digitsLengthOK = false;

        //Check for letters only
        if(!nameText.getText().toString().matches(Constants.REGULAR_EXPRESSION_LETTERS_ONLY)){
            //nameText.requestFocus();
            nameText.setError(getString(R.string.regex_letters_only));
        }else
            nameOK = true;

        //Check for numbers only
        if(!phoneText.getText().toString().matches(Constants.REGULAR_EXPRESSION_NUMBERS_ONLY)){
            //phoneText.requestFocus();
            phoneText.setError(getString(R.string.regex_numbers_only));
        }else
            phoneOK = true;

        //Check for correct number of digits
        if(!phoneText.getText().toString().matches(Constants.REGULAR_EXPRESSION_8_TO_12_DIGITS)){
            //phoneText.requestFocus();
            phoneText.setError(getString(R.string.regex_8_to_12_digits));
        }else
            digitsLengthOK = true;

        return (nameOK && phoneOK && digitsLengthOK);
    }

    private void showLog(){
        Log.i(Constants.TAG_INPUT, nameText.getText().toString());
        Log.i(Constants.TAG_INPUT, phoneText.getText().toString());
        String dateInput = bDate.getDayOfMonth() + "." + bDate.getMonth() + "." + bDate.getYear();
        Log.i(Constants.TAG_INPUT, dateInput);
    }

    private Person getInputData(){
        String name = nameText.getText().toString();
        String pNumber = phoneText.getText().toString();
        long unixDate = bDate.getCalendarView().getDate();
        Date birthdayDate = new Date(unixDate);

        if(isEditSession){
            buddyToEdit.setName(name);
            buddyToEdit.setPhoneNumber(pNumber);
            buddyToEdit.setBirthdayDate(birthdayDate);
            return buddyToEdit;
        }

        return new Person(name, pNumber, birthdayDate);
    }

    public void addMessage(View view){
        if(isValid()) {
            showLog();
            Intent addNewMessage = new Intent(RegisterPerson.this, RegisterMessage.class);
            addNewMessage.putExtra(Constants.TAG_PERSON, getInputData());
            if(isEditSession)
                addNewMessage.putExtra(Constants.IS_EDIT_SESSION, true);
            addNewMessage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            RegisterPerson.this.startActivity(addNewMessage);
        }
    }


}
