package com.example.s198569.databaseeks;

/**
 * Created by luke on 9/15/15.
 */
public class Kontakt {

    private int _ID;
    private String navn;
    private int telefon;

    public Kontakt() {
    }

    public Kontakt(String navn, int telefon) {
        this.navn = navn;
        this.telefon = telefon;
    }

    public Kontakt(int _ID, String navn, int telefon) {
        this._ID = _ID;
        this.navn = navn;
        this.telefon = telefon;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }
}
