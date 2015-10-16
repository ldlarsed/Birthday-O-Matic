package com.example.s198569_mappe2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.s198569_mappe2.services.BDayService;

public class MainActivity extends AppCompatActivity {

    private BDayService bDayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   DBHandler db = new DBHandler(this);
        ArrayList<Person> pl = db.getAllBuddies();
        for(Person p : pl){
            Log.i("Person", p.toString());
        }*/

        Intent intent = new Intent(this, BDayService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addNew(View view){
        Intent addNewActivity = new Intent(MainActivity.this, RegisterPerson.class);
        addNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //This should prevent stacking the activities
        //this.getApplicationContext().startActivity(addNew);
        MainActivity.this.startActivity(addNewActivity);
    }

    public void showBuddyList(View view){
        Intent addNewActivity = new Intent(MainActivity.this, BuddyList.class);
        addNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //This should prevent stacking the activities
        //this.getApplicationContext().startActivity(addNew);
        MainActivity.this.startActivity(addNewActivity);
    }

    public void showPreferences(View view){
        Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            BDayService.MyBinder b = (BDayService.MyBinder) binder;
            bDayService = b.getService();
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            bDayService = null;
        }
    };


}
