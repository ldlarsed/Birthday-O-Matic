package com.example.luke.jsonasync;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView jasontekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jasontekst = (TextView) findViewById(R.id.jasontekst);

        HentVerdier hent = new HentVerdier();
        hent.execute();
    }

    private class HentVerdier extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = null;
            InputStream is = null;
            StringBuilder sb = null;
            ArrayList<NameValuePair> data = new ArrayList<NameValuePair>(1);

            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://www.cs.hioa.no/~torunngj/jsonout.php");
                httpPost.setEntity(new UrlEncodedFormEntity(data));
                HttpResponse response = httpClient.execute(httppost);
            }
        }
    }
}
