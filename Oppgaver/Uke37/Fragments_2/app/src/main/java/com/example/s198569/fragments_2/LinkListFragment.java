package com.example.s198569.fragments_2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by luke on 10/13/15.
 */
public class LinkListFragment extends Fragment {

    private static ArrayAdapter<String> la;
    private static UrlEndret listener;
    public interface UrlEndret{
        public void linkEndret(String link);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (UrlEndret) activity;
            System.out.println("satt opp lytter");
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
            + " must implement OnTitleSelectedListener");
        }
    }


    //Constructor
    public LinkListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.linklist_layout, container, false);
        ListView lv = (ListView) v.findViewById(R.id.urls);

        String[] values = new String[] {"http://www.vg.no", "http://www.hioa.no"};
        final ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < values.length; ++i)
            list.add(values[i]);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = adapter.getItem(position);
                listener.linkEndret(data);
            }
        });
        return v;
    }
}
