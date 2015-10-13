package com.example.s198569.fragments_2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by luke on 10/13/15.
 */
public class MinUrl extends Fragment {

    private String currentURL;

    public void init(String url){
        currentURL = url;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.web_layout, container, false);
        if(currentURL != null){
            WebView wv = (WebView) v.findViewById(R.id.webPage);
            wv.loadUrl(currentURL);
        }
        return v;
    }

    public void updateUrl(String url){
        currentURL = url;
        WebView wv = (WebView) getView().findViewById(R.id.webPage);
        wv.loadUrl(url);
    }


}
