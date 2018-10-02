package com.pikchillytechnologies.tagtaxiservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pikchillytechnologies.tagtaxiservice.R;

public class SplashActivity extends AppCompatActivity {

    // Declaration of Time duration till which splash screen is presented to user
    private final static int TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Move the user once time has elapsed to Sign In Screen
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}
