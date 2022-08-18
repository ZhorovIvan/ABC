package com.ivanzhorov.abcc.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ivanzhorov.abcc.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent generalIntent = new Intent(MainActivity.this, GeneralActivity.class);
                startActivity(generalIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void hideStatusBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}