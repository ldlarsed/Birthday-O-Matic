package com.example.s198569_mappe2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.DAL.DBHandler;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.R;

import java.util.ArrayList;

/**
 * Created by luke on 10/15/15.
 */
public class BuddyListFragment extends Fragment {

    private static ArrayAdapter<String> bdAdapter;
    private static BuddylistEvent buddyListener;
    private DBHandler db;

    public interface BuddylistEvent{
        void buddyChanged(Person p);
    }


    public BuddyListFragment() {
    }

  /*  public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            buddyListener = (BuddylistEvent) activity;
        }catch (ClassCastException e){
            Log.e(Constants.TAG_EXCEPTION, Constants.TAG_EXCEPTION_IN + activity.toString() + ": "+e);
            throw new ClassCastException();
        }
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.buddy_list_layout, container, false);
        ListView lv = (ListView) v.findViewById(R.id.buddylistBuddies);

        db = new DBHandler(getActivity());

        final ArrayList<Person> bList = db.getAllBuddies();
        final ArrayList<String> buddyDetails = new ArrayList<String>();

        buddyDetails.add(bList.get(0).getName());

        /*for (Person s : bList)
            buddyDetails.add(s.getName());*/

        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, buddyDetails);

        lv.setAdapter(adapter);

        //Husk å sette en listener for hver item her.
        //db.close();

        return v;
    }
}
