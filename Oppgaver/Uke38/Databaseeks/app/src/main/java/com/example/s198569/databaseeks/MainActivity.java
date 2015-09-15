package com.example.s198569.databaseeks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);

        //leggTilNyeKontakter();
        //listeAlleKontakter();
        visAntallKontakter();
    }

    private void leggTilNyeKontakter(){
        Log.d("Legg inn: ", "legger til kontakter");

        db.leggTilKontakt(new Kontakt("Ole", 91000000));
        db.leggTilKontakt(new Kontakt("Anne", 91999999));
        db.leggTilKontakt(new Kontakt("Tommy", 95222222));
        db.leggTilKontakt(new Kontakt("Annika", 95333333));
    }

    private void listeAlleKontakter(){
        List<Kontakt> kontakter = db.finnAlleKontakter();

        for(Kontakt kont : kontakter){
            String log = "Id: " + kont.get_ID() + "\t Navn: " + kont.getNavn() +
                    "\t Telefon: " + kont.getTelefon();
            Log.d("Navn:", log);
        }
    }

    private void visAntallKontakter(){
        Log.d("Antall", String.valueOf(db.finnAntallKontakter()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
