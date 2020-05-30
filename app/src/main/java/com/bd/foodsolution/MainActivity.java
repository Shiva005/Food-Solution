package com.bd.foodsolution;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.foodsolution.Adapter.HorizontalRecyclerViewAdapter;
import com.bd.foodsolution.Adapter.VerticleRecyclerViewAdapter;
import com.bd.foodsolution.orderdetails.YourOrder;
import com.bd.foodsolution.authentication.LoginActivity;
import com.bd.foodsolution.authentication.ShowProfileActivity;
import com.bd.foodsolution.authentication.UpdatePassword;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RewardedVideoAdListener {
    private List names = new ArrayList<>(Arrays.asList("Juice", "Sandwich", "Pizza ", "Hamburger", "Milk", "Cake"));
    private List imageUrl = new ArrayList<>(Arrays.asList(R.drawable.juice, R.drawable.sandwich, R.drawable.pizza, R.drawable.hjamburger, R.drawable.milk, R.drawable.cake));

    private List FoodName = new ArrayList<>(Arrays.asList("Biriyani", "Burger", " Burger ", "Donot", "Fruits", "Plates", "Spoons", "Vegetables"));
    private List FoodImage = new ArrayList<>(Arrays.asList(R.drawable.biriyani, R.drawable.burger, R.drawable.rotiburger, R.drawable.donot, R.drawable.fruits, R.drawable.plates, R.drawable.spoon, R.drawable.vegetables));
    private List FoodPrice = new ArrayList<>(Arrays.asList("140", "100", "20", "50", "500", "200", "140", "110"));

    private boolean exit = false;
    private String Phone = "+917814098910";
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private View mHeaderView;
    private TextView userName,userGmail;
    private CircleImageView circleImageView;
    private FirebaseUser user;
    private Uri personPhoto;
    private RewardedVideoAd mRewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        MobileAds.initialize(this, getString(R.string.mRewardedVideoAd));
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + Phone)));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mHeaderView = navigationView.getHeaderView(0);
        user = firebaseAuth.getCurrentUser();
        setUserDetails();

        initHorizontalRecyclerView();
        initVerticleRecyclerView();
    }


    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.mRewardedVideoAd2),
                new AdRequest.Builder().build());
    }


    private void setUserDetails() {

        user=firebaseAuth.getCurrentUser();
        String displayName, email;
        userName = mHeaderView.findViewById(R.id.userGamilName);
        userGmail = mHeaderView.findViewById(R.id.mail);


        //if (user != null) {
            displayName = user.getDisplayName();
            for (UserInfo userInfo : user.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
                    userName.setText(displayName);
                }
            }
            email = user.getEmail();
            userGmail.setText(email);
        //}
    }

    @Override
    public void onBackPressed() {

        if (exit){
            super.onBackPressed();
        }
        else
        {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                int delay = 2000;
                exit = true;
                Toast.makeText(this, "Click once again to EXIT !!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }

                }, delay);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_Share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Quality food is our priority.");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.bd.foodsolution");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share link!"));

        }
        else if(id==R.id.action_Rate){
            try {
                Uri marketUri = Uri.parse("market://details?id=com.bd.foodsolution");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                startActivity(marketIntent);
            }catch(ActivityNotFoundException e) {
                Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=com.bd.foodsolution");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                startActivity(marketIntent);
            }
            return true;
        }
        else if(id== R.id.showprofile)
        {
            startActivity(new Intent(MainActivity.this, ShowProfileActivity.class));
            return true;
        }
        else if(id==R.id.Updateprofile){
            startActivity(new Intent(MainActivity.this, UpdatePassword.class));
            return true;
        }

        else if (id== R.id.usr_donate)
        {
            if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
            }
            return true;
        }


        else if(id==R.id.Logout){
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.your_order:
                startActivity(new Intent(MainActivity.this, YourOrder.class));
                break;
            case R.id.Toady_Special:
                Toast.makeText(MainActivity.this, "Not Available ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.our_special:
                Toast.makeText(MainActivity.this, "Not Available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.policy_item:
                startActivity(new Intent(MainActivity.this, PolicyActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                //Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Feed_back:
                    startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                break;
            case R.id.Show_profile :
                startActivity(new Intent(MainActivity.this, ShowProfileActivity.class));
                break;
                    /*case R.id.Update_profile:
                        startActivity(new Intent(MainActivity.this, UpdateProfile.class));
                        break;*/
            case R.id.Reset_Password:
                startActivity(new Intent(MainActivity.this, UpdatePassword.class));
                break;
            case R.id.log_out:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

            default:
                Toast.makeText(MainActivity.this, "Invalid Selection", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initHorizontalRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.horizontalRecyclerview);
        recyclerView.setLayoutManager(layoutManager);
        HorizontalRecyclerViewAdapter adapter = new HorizontalRecyclerViewAdapter(this, names, imageUrl);
        recyclerView.setAdapter(adapter);
    }

    private void initVerticleRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.verticleRecyclerview_id);
        VerticleRecyclerViewAdapter adapter = new VerticleRecyclerViewAdapter(this, FoodName, FoodImage, FoodPrice);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

}