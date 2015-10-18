package com.example.s198569_mappe2.BLL;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by luke on 10/18/15.
 */
public class BuddyListAdapter extends ArrayAdapter<Person> {

    Context context;
    int resource;
    ArrayList<Person> buddies = null;

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

            row.setTag(bHolder);
        }else{
            bHolder = (BuddyHolder) row.getTag();
        }

        Person buddy = buddies.get(position);
        bHolder.txtName.setText(buddy.getName());
        bHolder.bDayDate.setText(buddy.getSimpleBirthdayDate());
        bHolder.swtchOnOff.setChecked(buddy.isActive());

        return row;
    }

    static class BuddyHolder{
        TextView txtName, bDayDate;
        Switch swtchOnOff;
    }
}
