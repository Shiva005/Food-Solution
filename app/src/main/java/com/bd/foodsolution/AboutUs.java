package com.bd.foodsolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUs extends AppCompatActivity {

    private AdView mAdView;
    private Toolbar toolbar;
    private CircleImageView circleImageView1,circleImageView2;
    private TextView ohidText1,ohidText2,shivaText1,shivaText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar = findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this,getString(R.string.adUnitIdMain));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        circleImageView2 = findViewById(R.id.shiva_photo);
        shivaText1 = findViewById(R.id.shiva_name);
        shivaText2 = findViewById(R.id.shiva_description);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
