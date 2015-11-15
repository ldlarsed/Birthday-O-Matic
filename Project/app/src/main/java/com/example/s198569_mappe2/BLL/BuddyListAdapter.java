package com.example.s198569_mappe2.BLL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import com.example.s198569_mappe2.LIB.DataSetChangedListener;
import com.example.s198569_mappe2.LIB.DialogYesNoListener;
import com.example.s198569_mappe2.R;
import com.example.s198569_mappe2.RegisterMessage;
import com.example.s198569_mappe2.RegisterPerson;
import com.example.s198569_mappe2.fragments.InfoDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by luke on 10/18/15.
 */
public class BuddyListAdapter extends ArrayAdapter<Person> {

    Context context;
    int resource, buddyId;
    ArrayList<Person> buddies = null;
    private DBHandler db;
    private DataSetChangedListener dataSetListener;


    public BuddyListAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.buddies = objects;
    }

    public void setDataChangedSetListener(DataSetChangedListener listener){
        this.dataSetListener = listener;
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
        bHolder.bDayDate.setText(buddy.getSimpleYearMonthDay());
        bHolder.swtchOnOff.setChecked(buddy.isActive());

        bHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "Delete clicked");
                buddyId = buddy.get_ID();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);


                builder.setMessage(R.string.info_dialog_title_delete_buddy).setPositiveButton(R.string.answer_yes, dialogClickListener)
                        .setNegativeButton(R.string.answer_no, dialogClickListener).show();

            }
        });

        bHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "Edit clicked");

                Intent editBuddy = new Intent(getContext(), RegisterPerson.class);
                editBuddy.putExtra(Constants.TAG_PERSON, buddy);
                editBuddy.putExtra(Constants.IS_EDIT_SESSION, true);
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


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    db = new DBHandler(getContext());
                    db.deleteBuddy(buddyId);
                    if(dataSetListener != null){
                        dataSetListener.dataSetChanged(true);
                    }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    static class BuddyHolder{
        TextView txtName, bDayDate;
        Switch swtchOnOff;
        ImageButton editButton, deleteButton;
    }
}
