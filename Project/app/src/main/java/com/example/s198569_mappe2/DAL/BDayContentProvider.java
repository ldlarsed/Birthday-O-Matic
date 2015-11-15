package com.example.s198569_mappe2.DAL;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.SimpleCursorAdapter;

import com.example.s198569_mappe2.BOL.Person;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luke on 10/20/15.
 */
public class BDayContentProvider extends ContentProvider {


    static final String PROVIDER_NAME = "com.example.s198569_mappe2.BDayOMatic";
    static final String URL = "content://" + PROVIDER_NAME + "/BDayBuddies";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "name";
    static final String BDAY = "bday";

    private static HashMap<String, String> BUDDIES_PROJECTION_MAP;

    static final int BUDDIES = 1;
    static final int BUDDY_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "BDayBuddies", BUDDIES);
        uriMatcher.addURI(PROVIDER_NAME, "BDayBuddies/#", BUDDY_ID);
    }

    /*private DBHandler database = new DBHandler(getContext());
    SQLiteDatabase db = database.getWritableDatabase();*/

    static final String DATABASE_NAME = "BDayOMatic";
    static final String BUDDIES_TABLE_NAME = "BDayBuddies";
    static final int DATABASE_VERSION = 2;


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        DBHandler db = new DBHandler(getContext());
        ArrayList<Person> liste = db.getAllBuddies();

        String[] columns = new String[] {"_id", "buddy", "bday"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        for(Person p : liste){
            matrixCursor.addRow(new Object[] {p.get_ID(), p.getName(), p.getSimpleBirthdayDate()});
        }


        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
                return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
