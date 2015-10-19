package com.example.s198569_mappe2.BLL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.DAL.DBHandler;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.R;
import com.example.s198569_mappe2.RegisterMessage;
import com.example.s198569_mappe2.RegisterPerson;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by luke on 10/18/15.
 */
public class BuddyListAdapter extends ArrayAdapter<Person> {

    Context context;
    int resource;
    ArrayList<Person> buddies = null;
    private DBHandler db;


    public BuddyListAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.buddies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View row = convertView;
        BuddyHolder bHolder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            bHolder = new BuddyHolder();
            bHolder.txtName = (TextView) row.findViewById(R.id.buddylistitemrowTextName);
            bHolder.bDayDate = (TextView) row.findViewById(R.id.buddylistitemrowTextBDay);
            bHolder.swtchOnOff = (Switch) row.findViewById(R.id.buddylistitemrowSwitch);
            bHolder.deleteButton = (ImageButton) row.findViewById(R.id.buddylistitemrowDelete);
            bHolder.editButton = (ImageButton) row.findViewById(R.id.buddylistitemrowEdit);

            row.setTag(bHolder);
        }else{
            bHolder = (BuddyHolder) row.getTag();
        }

        final Person buddy = buddies.get(position);
        bHolder.txtName.setText(buddy.getName());
        bHolder.bDayDate.setText(buddy.getSimpleBirthdayDate());
        bHolder.swtchOnOff.setChecked(buddy.isActive());

        bHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "Delete clicked");
                db = new DBHandler(getContext());
                db.deleteBuddy(buddy.get_ID());
            }
        });

        bHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "Edit clicked");

                Intent editBuddy = new Intent(getContext(), RegisterPerson.class);
                editBuddy.putExtra(Constants.TAG_PERSON, buddy);
                editBuddy.putExtra("TO_EDIT", true);
                context.startActivity(editBuddy);
            }
        });

        bHolder.swtchOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "On/Off switch changed");
                db = new DBHandler(getContext());
                db.changeStatus(buddy.get_ID(), !buddy.isActive());
            }
        });

        return row;
    }

    static class BuddyHolder{
        TextView txtName, bDayDate;
        Switch swtchOnOff;
        ImageButton editButton, deleteButton;
    }
}
