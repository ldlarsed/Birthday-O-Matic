package com.example.s198569.kontakter;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by luke on 10/6/15.
 */
public class Contacts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private LoaderManager loaderManager;
    private CursorLoader cursorLoader;
    private SimpleCursorAdapter mAdapter;
    private String TAG = "Loader";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.liste, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loaderManager = getActivity().getLoaderManager();
        String[] uiBindFrom = {ContactsContract.Contacts.DISPLAY_NAME};
        int[] uiBindTo = {android.R.id.text1};

        mAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, null, uiBindFrom, uiBindTo, 0);
        ListView l = (ListView) getActivity().findViewById(R.id.listview);
        l.setAdapter(mAdapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getActivity().getBaseContext(), arg2 + " klikket", Toast.LENGTH_SHORT).show();
            }
        });
        loaderManager.initLoader(0, null, this);
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        cursorLoader = new CursorLoader(getActivity().getBaseContext(), ContactsContract.Contacts.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        if(mAdapter != null && cursor != null)
            mAdapter.swapCursor(cursor); //swap the cursor in
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> arg0) {
        if(mAdapter != null)
            mAdapter.swapCursor(null);
        else
            Log.v(TAG, "OnLoadFinished: mAdapter is null");
    }
}
