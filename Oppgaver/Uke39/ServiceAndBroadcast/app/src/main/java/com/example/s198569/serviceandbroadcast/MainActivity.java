package com.example.s198569.serviceandbroadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMyService(View v){
        Intent intent = new Intent(this, MyService.class);
        this.startService(intent);

        intent.setAction("com.example.s198569.ServiceAndBroadcast.mybroadcast");
        sendBroadcast(intent);
    }
}
