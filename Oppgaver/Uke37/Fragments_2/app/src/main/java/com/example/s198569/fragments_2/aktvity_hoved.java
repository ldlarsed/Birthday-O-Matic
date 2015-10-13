package com.example.s198569.fragments_2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by luke on 10/13/15.
 */
public class aktvity_hoved extends Activity implements LinkListFragment.UrlEndret {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktvity_hoved);
    }

    @Override
    public void linkEndret(String link) {
        if (findViewById(R.id.fragPage) != null) {
            MinUrl wvf = (MinUrl) getFragmentManager().findFragmentById((R.id.fragPage));

            if (wvf == null) {
                wvf = new MinUrl();
                wvf.init(link);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.fragPage, wvf);
                ft.commit();
            } else {
                wvf.updateUrl(link);
            }
        } else {
            Intent i = new Intent(this, WebViewActivity.class);
            i.putExtra("link", link);
            startActivity(i);
        }
    }
}


