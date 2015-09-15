package com.example.s198569.databaseeks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 9/15/15.
 */
public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Navn";
    static String KEY_PH_NO = "Telefon";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Telefonkontakter";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_TABELL = "CREATE TABLE " + TABLE_KONTAKTER + " (" + KEY_ID +
                " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," +
                KEY_PH_NO + " INTEGER" + ")";

        Log.d("SQL", LAG_TABELL);
        try {
            db.execSQL(LAG_TABELL);
        } catch (SQLiteException e) {
            Log.w("SQL", "Problem med oppretting av tabellen");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        onCreate(db);
    }

    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, kontakt.getNavn());
        values.put(KEY_PH_NO, kontakt.getTelefon());

        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

    public List<Kontakt> finnAlleKontakter() {
        List<Kontakt> kontaktListe = new ArrayList<Kontakt>();

        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();

                kontakt.set_ID(cursor.getInt(0));
                kontakt.setNavn(cursor.getString(1));
                kontakt.setTelefon(Integer.parseInt(cursor.getString(2)));
                kontaktListe.add(kontakt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return kontaktListe;
    }

    public int finnAntallKontakter(){
        String sql = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }

    /**
     * Ikke testet.
     * @param kontakt
     * @return
     */
    public int oppdaterKontakt(Kontakt kontakt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, kontakt.getNavn());
        values.put(KEY_PH_NO, kontakt.getTelefon());

        int endret = db.update(TABLE_KONTAKTER, values, KEY_ID + "= ?", new String[]
                {String.valueOf(kontakt.get_ID())});
        db.close();
        return endret;
    }
}
