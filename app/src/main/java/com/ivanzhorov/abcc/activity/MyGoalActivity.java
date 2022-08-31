package com.ivanzhorov.abcc.activity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.ivanzhorov.abcc.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MyGoalActivity extends AppCompatActivity {

    private int currentApiVersion;
    private Switch switch1, switch2, switch3, switch4, switch5;
    private Button addButton, removeButtonButton;
    private TextView goal1, goal2, goal3, goal4, goal5;
    private EditText userInput;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goal);
        hideStatusBar();
        hideSystemButtons();
        initAllEntities();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initAllEntities() {
        addButton = (Button) findViewById(R.id.add_new_goal);
        removeButtonButton = (Button) findViewById(R.id.remove_button);

        goal1 = findViewById(R.id.goal1);
        goal2 = findViewById(R.id.goal2);
        goal3 = findViewById(R.id.goal3);
        goal4 = findViewById(R.id.goal4);
        goal5 = findViewById(R.id.goal5);

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);
        switch4 = findViewById(R.id.switch4);
        switch5 = findViewById(R.id.switch5);

        userInput = (EditText) findViewById(R.id.input_goal);

        hideUnusedEntities(
                new ArrayList<Switch>(Arrays.asList(switch1, switch2, switch3, switch4, switch5)),
                new ArrayList<TextView>(Arrays.asList(goal1, goal2, goal3, goal4, goal5)),
                userInput);

        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //If the keyevent is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    enterPushed();
                    return true;
                }
                return false;
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setVisibility(View.VISIBLE);
            }
        });
        removeButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAllTurnOnSwitches();
            }
        });
    }

    private void closeAllTurnOnSwitches() {
        //If some switches are turn on then hide them all
    }

    private void enterPushed() {
        userInput.setVisibility(View.INVISIBLE);
        String userGoal = String.valueOf(userInput.getText());
        if (userGoal.equals("")) {
            //write logic for empty input
        }else{
            chooseWriteRowForFilling(userGoal);
        }
    }

    private void chooseWriteRowForFilling(String userGoal) {
        goal1.setText(userGoal);
        switch1.setVisibility(View.VISIBLE);
        goal1.setVisibility(View.VISIBLE);
        userInput.setText("");
        closeKeyboard();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void hideUnusedEntities(ArrayList<Switch> switches,
                                    ArrayList<TextView> textGoals,
                                    EditText input) {
        textGoals.forEach(textGoal -> textGoal.setVisibility(View.INVISIBLE));
        switches.forEach(goalSwitch -> goalSwitch.setVisibility(View.INVISIBLE));
        input.setVisibility(View.INVISIBLE);
    }

    private void hideSystemButtons() {
        currentApiVersion = android.os.Build.VERSION.SDK_INT;
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void hideStatusBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();
        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}