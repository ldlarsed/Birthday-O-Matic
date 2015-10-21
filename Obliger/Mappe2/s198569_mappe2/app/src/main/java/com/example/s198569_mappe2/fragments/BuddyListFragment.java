package com.example.s198569_mappe2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.s198569_mappe2.BLL.BuddyListAdapter;
import com.example.s198569_mappe2.BOL.Person;
import com.example.s198569_mappe2.BuddyList;
import com.example.s198569_mappe2.DAL.DBHandler;
import com.example.s198569_mappe2.LIB.Constants;
import com.example.s198569_mappe2.LIB.DataSetChangedListener;
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
        final ListView lv = (ListView) v.findViewById(R.id.buddylistBuddies);

        db = new DBHandler(getActivity());

        final ArrayList<Person> bList = db.getAllBuddies();
        final ArrayList<String> buddyDetails = new ArrayList<String>();


        for (Person s : bList)
            buddyDetails.add(s.getName());



        final BuddyListAdapter adapter = new BuddyListAdapter(getActivity(), R.layout.buddy_list_item_row, bList);



        View header = inflater.inflate(R.layout.buddy_list_header, null);



        adapter.setDataChangedSetListener(new DataSetChangedListener() {
            @Override
            public void dataSetChanged(boolean isChanged) {
                Log.i("Listener", "Adapter data set has been changed");
                lv.destroyDrawingCache();
                lv.setVisibility(ListView.INVISIBLE);

                adapter.notifyDataSetChanged();
                lv.setVisibility(ListView.VISIBLE);
                getActivity().recreate();
            }
        });


        lv.addHeaderView(header);
        lv.setAdapter(adapter);

        return v;
    }
}
