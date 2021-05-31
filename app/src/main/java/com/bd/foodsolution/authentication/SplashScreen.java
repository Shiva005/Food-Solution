package com.bd.foodsolution.authentication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bd.foodsolution.MainActivity;
import com.bd.foodsolution.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            imageView = findViewById(R.id.splashid);

            firebaseAuth = FirebaseAuth.getInstance();
            //this.getSupportActionBar().hide();

            new Thread((new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);

                    }
                    catch (InterruptedException ae){
                        Toast.makeText(SplashScreen.this, "Error while Opening the App", Toast.LENGTH_SHORT).show();
                    }

                    //check user login or not
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if(user != null){
                        //finish();
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }
                    else {

                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        //When going to login activity than splash screen is close for these finish.
                    }
                    finish();
                }
            })).start();
        }
    }