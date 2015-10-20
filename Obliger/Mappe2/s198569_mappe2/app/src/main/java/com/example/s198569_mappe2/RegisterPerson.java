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

import java.util.Calendar;
import java.util.Date;

//TODO: Prevent setting birthday date in the future.
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        switch (id){
            case android.R.id.home:
                this.finish();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean isValid(){
        boolean nameOK = false;
        boolean phoneOK = false;
        if(!nameText.getText().toString().matches(Constants.REGULAR_EXPRESSION_LETTERS_ONLY)){
            //nameText.requestFocus();
            nameText.setError(getString(R.string.regex_letters_only));
        }else
            nameOK = true;
        if(!phoneText.getText().toString().matches(Constants.REGULAR_EXPRESSION_NUMBERS_ONLY)){
            //phoneText.requestFocus();
            phoneText.setError(getString(R.string.regex_numbers_only));
        }else
            phoneOK = true;
        return (nameOK && phoneOK);
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
