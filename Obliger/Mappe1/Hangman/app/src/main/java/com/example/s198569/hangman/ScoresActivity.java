package com.example.s198569.hangman;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s198569.hangman.lib.HangmanDataSource;
import com.example.s198569.hangman.lib.Player;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    private HangmanDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        /* Datasource connection */
        datasource = new HangmanDataSource(this);
        try {
            datasource.open();
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Could not connect to the database.", Toast.LENGTH_SHORT);
            toast.show();
        }
        /* End of database conncetion */

         /* Existing Players List*/
        ArrayList<Player> registeredPlayers = (ArrayList<Player>) datasource.getAllPlayers();
        if(registeredPlayers == null){
            Toast.makeText(this, "No registered players yet", Toast.LENGTH_SHORT).show();
        }else {
            String allFolks = "";
            for(Player p : registeredPlayers){
                allFolks += p.getName() + " " + p.getScore() + "\n";
            }
            Toast.makeText(this, allFolks, Toast.LENGTH_SHORT).show();
        }
        /* End of existing players list */

        /* Setting the mockup scores */
        TableLayout scores = (TableLayout) findViewById(R.id.layout_scores_table);
        XmlResourceParser mockupScores = getResources().getXml(R.xml.scores);

        setHeaderRow(scores);
        try{
            //showScores(scores, mockupScores);
            showScores(scores, registeredPlayers);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, getResources().getString(R.string.err_scores), Toast.LENGTH_SHORT);
            toast.show();
            Log.e(getResources().getString(R.string.exception), getResources().getString(R.string.err_scores), e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_scores, menu);
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

    /**
     * Set up the first row in a table
     * @param scoreTable
     */
    private void setHeaderRow(TableLayout scoreTable){
        final TableRow headerRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.secondary_2_1);
        float textSize = getResources().getDimensionPixelSize(R.dimen.default_text_size);

        addTextToRowWithValues(headerRow, getResources().getString(R.string.score_ranking), textColor, textSize);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.score_username), textColor, textSize);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.score_score), textColor, textSize);


        scoreTable.addView(headerRow);
    }

    /**
     * Inserts the score rows
     * @param scoreTable
     * @param scoreValue
     * @param scoreRank
     * @param scorePlayerName
     */
    private void insertScoreRow(final TableLayout scoreTable, String scoreRank, String scoreValue, String scorePlayerName){
        final TableRow newRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.secondary_1_2);
        float textSize = getResources().getDimension(R.dimen.table_text);

        addTextToRowWithValues(newRow, scoreRank, textColor, textSize);
        addTextToRowWithValues(newRow, scoreValue, textColor, textSize);
        addTextToRowWithValues(newRow, scorePlayerName, textColor, textSize);

        scoreTable.addView(newRow);
    }

    /**
     * Helper method thath adds a single row to the end of the current table view
     * @param tableRow
     * @param text
     * @param textColor
     * @param textSize
     */
    private void addTextToRowWithValues(final TableRow tableRow, String text, int textColor, float textSize){
        TextView textView = new TextView(this);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setText(text);
        tableRow.addView(textView);
    }


    /**
     * Fetching data from the scores.xml
     * @param scoreTable
     * @param scores
     * @throws XmlPullParserException
     */
/*    private void showScores(final TableLayout scoreTable, XmlResourceParser scores) throws XmlPullParserException, IOException{
        int eventType = -1;
        boolean foundScores = false;

        while(eventType != XmlResourceParser.END_DOCUMENT){
            if(eventType == XmlResourceParser.START_TAG){
                String strName = scores.getName();
                if(strName.equals("score")){
                    foundScores = true;
                    String scoreValue = scores.getAttributeValue(null, "score");
                    String scorePlayerName = scores.getAttributeValue(null, "username");
                    String scorePlayerRank = scores.getAttributeValue(null, "rank");
                    insertScoreRow(scoreTable, scorePlayerRank, scorePlayerName, scoreValue);
                }
            }
            eventType = scores.next();
        }
    }*/

    /**
     * Sets a table from a sorted list of player objects. Sorted after the score rank.
     * @param scoreTable
     * @param sortedPlayers
     */
    private void showScores(final TableLayout scoreTable, ArrayList<Player> sortedPlayers){
        int rank = 1;
        for(Player p : sortedPlayers){
            insertScoreRow(scoreTable, String.valueOf(rank++), p.getName(), String.valueOf(p.getScore()));
        }
    }

    @Override
    protected void onResume(){
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        datasource.close();
        super.onDestroy();
    }
}
