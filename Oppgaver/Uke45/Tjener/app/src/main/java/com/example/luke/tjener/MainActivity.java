package com.example.luke.tjener;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.startServer);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartServer sS = new StartServer();
                sS.execute();
            }
        });

    }


    private class StartServer extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            try{
                ServerSocket servsock = new ServerSocket(12345);

                while(true){
                    Socket s = servsock.accept();
                    Thread t = new Thread(new ServerKlient(s));
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return("Ferdig");
        }
    }
}
