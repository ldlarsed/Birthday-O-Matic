package com.example.s198569.hangman;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Scores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_scores);

        /* Setting the mockup scores */
        TableLayout scores = (TableLayout) findViewById(R.id.layout_scores_table);
        XmlResourceParser mockupScores = getResources().getXml(R.xml.scores);

        setHeaderRow(scores);
        try{
            showScores(scores, mockupScores);
        }catch (Exception e){
            Log.e("SCORES", "Failed to load scores", e);
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

    private void setHeaderRow(TableLayout scoreTable){
        final TableRow headerRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.secondary_1_2);
        float textSize = getResources().getDimensionPixelSize(R.dimen.default_text_size);

        addTextToRowWithValues(headerRow, getResources().getString(R.string.score_username), textColor, textSize);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.score_score), textColor, textSize);
        addTextToRowWithValues(headerRow, getResources().getString(R.string.score_ranking), textColor, textSize);
        scoreTable.addView(headerRow);
    }

    private void insertScoreRow(final TableLayout scoreTable, String scoreValue, String scoreRank, String scorePlayerName){
        final TableRow newRow = new TableRow(this);
        int textColor = getResources().getColor(R.color.secondary_1_1);
        float textSize = getResources().getDimension(R.dimen.default_text_size);

        addTextToRowWithValues(newRow, scorePlayerName, textColor, textSize);
        addTextToRowWithValues(newRow, scoreValue, textColor, textSize);
        addTextToRowWithValues(newRow, scoreRank, textColor, textSize);
        scoreTable.addView(newRow);
    }

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
    private void showScores(final TableLayout scoreTable, XmlResourceParser scores) throws XmlPullParserException, IOException{
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
                    insertScoreRow(scoreTable, scoreValue, scorePlayerName, scorePlayerRank);
                }
            }
            eventType = scores.next();
        }
    }
}
