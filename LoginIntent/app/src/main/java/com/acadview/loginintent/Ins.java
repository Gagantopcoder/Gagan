package com.acadview.loginintent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by gagan on 4/16/2018.
 */

public class Ins extends AppCompatActivity{

    private static final String LINK_ADDRESS = "LINK_ADDDRESS";
    private static final String BASE_ADDRESS = "https://www.google.com/";
    private static final String DEFAULT_ADDRESS ="";
    private WebView webView;
    private EditText editText;
    private Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ins);


        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        webView = (WebView)findViewById(R.id.webView1);
        webView.setWebViewClient(new WebViewClient());

        editText = (EditText)findViewById(R.id.search);

        editText.setText(settings.getString(LINK_ADDRESS, DEFAULT_ADDRESS));
        Button change = (Button)findViewById(R.id.butsearch);
        change.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View arg0) {
                final SharedPreferences.Editor editor = settings
                        .edit();


                editor.putString(LINK_ADDRESS, editText.getText().toString());
                editor.apply();

                reloadWebView();
            }

            private void reloadWebView() {
                webView.loadUrl(BASE_ADDRESS + editText.getText().toString());
            }
        });





        Intent i = getIntent();
        String userName = i.getStringExtra("userName");

        Toast.makeText(this, "Welcome:" +userName, Toast.LENGTH_SHORT).show();

    }





}
