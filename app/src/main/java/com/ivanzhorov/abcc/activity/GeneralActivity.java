package com.ivanzhorov.abcc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivanzhorov.abcc.R;

public class GeneralActivity extends AppCompatActivity {

    //private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        hideStatusBar();

        //button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openActivity2();
//            }
//        });
    }

//    public void openActivity2() {
//        Intent intent = new Intent(this, GeneralActivity.class);
//        startActivity(intent);
//    }
//
    private void hideStatusBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}