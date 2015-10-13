package com.example.s198569.contentproviderbok;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by luke on 10/6/15.
 */
public class BokCP extends ContentProvider {

    public static final String _ID = "_id";
    public static final String TITTEL = "Titel";
    private static final String DB_NAVN = "bok.db";
    private static final int DB_VERSJON = 2;
    private final static String TABELL = "bok";
    public final static String PROVIDER = "com.example.s198569.contentproviderbok";
    private static final int BOK = 1;
    private static final int MBOK = 2;

    DatabaseHelper DBhelper;
    SQLiteDatabase db;

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/bok");

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "bok", MBOK);
        uriMatcher.addURI(PROVIDER, "bok/#", BOK);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DB_NAVN, null, DB_VERSJON);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table " + TABELL + " (" + _ID + " integer primary key autoincrement, " + TITTEL + " text);";
            Log.d("DatabaseHelper", "oncreated sql" + sql);
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("drop table if exists " + TABELL );
            Log.d("DatabaseHelper", "updated");
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        DBhelper = new DatabaseHelper(getContext());
        db = DBhelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case MBOK:
                return "vnd.android.cursor.dir/vnd.example.bok";
            case BOK:
                return "vnd.android.cursor.vnd/vnd.example.bok";
            default:
                throw new IllegalArgumentException("Ugyldig URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        db.insert(TABELL, null, values);
        Cursor c = db.query(TABELL, null, null, null, null, null, null);
        c.moveToLast();
        long minid = c.getLong(0);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, minid);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
