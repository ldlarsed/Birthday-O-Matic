package com.example.s198569.fragments_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by luke on 10/13/15.
 */
public class WebViewActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = this.getIntent();
        String link = i.getExtras().getString("link");

        MinUrl wvf = new MinUrl();
        wvf.init(link);

        getFragmentManager().beginTransaction().add(android.R.id.content, wvf).commit();
    }
}
