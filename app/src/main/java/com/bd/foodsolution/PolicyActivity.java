package com.bd.foodsolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

public class PolicyActivity extends AppCompatActivity {

    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webview = new WebView(this);
        setContentView(webview);

        try {
            InputStream inputStream=this.getAssets().open("policy.html");
            int streamsize=inputStream.available();
            byte[] buffer=new byte[streamsize];
            inputStream.read(buffer);
            inputStream.close();
            String html=new String(buffer);
            webview.loadData(html, "text/html", "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
