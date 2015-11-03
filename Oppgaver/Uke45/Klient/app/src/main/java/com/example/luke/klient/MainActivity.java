package com.example.luke.klient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    int t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.send);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tall1 = (EditText) findViewById(R.id.tall1);
                EditText tall2 = (EditText) findViewById(R.id.tall2);

                t1 = Integer.parseInt(tall1.getText().toString());
                t2 = Integer.parseInt(tall2.getText().toString());

                SendStykke mk = new SendStykke();
                mk.execute();
            }
        });
    }


    private class SendStykke extends AsyncTask<String, Void, String>{


        private String TAG = "Client";
        private String IP = "10.0.2.2";
        private int PORT = 12345;
        String resultat;

        @Override
        protected String doInBackground(String... params) {

            Socket s = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try{
                s = new Socket(IP, PORT);
                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                out.println(t1 + ":" + t2);

                resultat = in.readLine();


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

                try {
                    out.close();
                    in.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return resultat;
        }


        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            EditText svar = (EditText) findViewById(R.id.sum);
            svar.setText(resultat);
        }
    }

}
