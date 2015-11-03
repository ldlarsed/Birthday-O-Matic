package com.example.luke.jsontest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.jasontekst);
        getJSON task = new getJSON();
        task.execute(new String[] {"http://www.cs.hioa.no/~torunngj/jsonout.php"});

    }




    private class getJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String s = "";
            String output = "";
            for(String url : urls){
                try{
                    URL urlen = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if(conn.getResponseCode() != 200){
                        throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    System.out.println("Output from server...\n");

                    while((s = br.readLine()) != null){
                        output = output + s;
                    }

                    conn.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
