package com.ivanzhorov.abcc.activity.MyGoalActivity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.ivanzhorov.abcc.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyGoalActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private int currentApiVersion;
    private Switch switch1, switch2, switch3, switch4, switch5;
    private Button addButton, removeButtonButton;
    private TextView goal1, goal2, goal3, goal4, goal5;
    private EditText userInput;
    private List<GoalRow> goalRows;
    private static final String USER_BOX_TITLE = "Dialog Box";
    private static final int MAX_SUM_GOAL_LETTERS = 32;
    private static final String FIELD_IS_EMPTY_MESSAGE = "Вы не написали цель";
    private static final String TOO_MANY_LETTERS_MESSAGE = "Ваша цель слишком длинная. Попробуйте сократить";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goal);
        hideStatusBar();
        hideSystemButtons();
        initAllEntities();
        restoreAllStates();
        setButtonListener();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initAllEntities() {
        addButton = findViewById(R.id.add_new_goal);
        removeButtonButton = findViewById(R.id.remove_button);

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

        userInput = findViewById(R.id.input_goal);
        userInput.setVisibility(View.INVISIBLE);

        //If it necessary you can add new goal row
        goalRows = new ArrayList(){{
            add(new GoalRow(goal1, switch1, "goal1", "switch1"));
            add(new GoalRow(goal2, switch2, "goal2", "switch2"));
            add(new GoalRow(goal3, switch3, "goal3", "switch3"));
            add(new GoalRow(goal4, switch4, "goal4", "switch4"));
            add(new GoalRow(goal5, switch5, "goal5", "switch5"));
        }};
    }

    private void setButtonListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushAddButton();
            }
        });
        removeButtonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAllTurnOnSwitches();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void restoreAllStates() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        goalRows.forEach(goalRow -> {
            //Think here
            goalRow.getGoalField().setText(prefs.getString(goalRow.getTextViewName(), ""));
            goalRow.getGoalSwitch().setChecked(prefs.getBoolean(goalRow.getSwitchName(), false));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        SharedPreferences.Editor editPrefs = prefs.edit();
//        editPrefs.putString("myProgress", "test");
//        editPrefs.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        SharedPreferences.Editor editPrefs = prefs.edit();
//        editPrefs.putString("myProgress", "test");
//        editPrefs.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Some code
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Some code
    }

    private void closeAllTurnOnSwitches() {
        for (GoalRow goalRow : goalRows) {
            boolean isSwitchTurnOn = goalRow.getGoalSwitch().isChecked();
            if (isSwitchTurnOn) {
                hideUnusualRow(goalRow.getGoalField(), goalRow.getGoalSwitch());
            }
        }
        sortGoalRows();
    }

    private void sortGoalRows() {
        //Sort rows by empty field
        for (GoalRow goalRow : goalRows) {
            String text = goalRow.getGoalField().getText().toString();
            if (text.equals("")) {
                changeRowPosition(goalRow);
            }
        }
    }

    private void changeRowPosition(GoalRow emptyRow) {
        GoalRow filledRow = gewFilledRow(getSubList(emptyRow));
        if (filledRow == null) {
            //
        } else {
            String text = filledRow.getGoalField().getText().toString();
            hideUnusualRow(filledRow.getGoalField(), filledRow.getGoalSwitch());
            //Write from filled row to empty
            showRow(emptyRow.getGoalField(), emptyRow.getGoalSwitch(), text);
        }
    }

    private void showRow(TextView textView, Switch goalSwitch, String text) {
        textView.setText(text);
        goalSwitch.setChecked(false);
        textView.setVisibility(View.VISIBLE);
        goalSwitch.setVisibility(View.VISIBLE);
    }

    private ArrayList<GoalRow> getSubList(GoalRow goalRow) {
        List<GoalRow> subList = goalRows.subList(goalRows.indexOf(goalRow), goalRows.size());
        return new ArrayList<>(subList);
    }

    private GoalRow gewFilledRow(ArrayList<GoalRow> goalRows) {
        if (goalRows == null) {
            return null;
        }
        for (GoalRow goalRow : goalRows) {
            if (!goalRow.getGoalField().getText().equals("")) {
                return goalRow;
            }
        }
        return null;
    }

    private void hideUnusualRow(TextView userGoal, Switch userSwitch) {
        userGoal.setText("");
        userSwitch.setChecked(false);
        userGoal.setVisibility(View.INVISIBLE);
        userSwitch.setVisibility(View.INVISIBLE);
    }

    private void pushAddButton() {
        userInput.setVisibility(View.VISIBLE);
        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //If the keyevent is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    enterPushed();
                    return true;
                }
                return false;
            }
        });
    }

    private void enterPushed() {
        userInput.setVisibility(View.INVISIBLE);
        String userGoal = String.valueOf(userInput.getText());
        if (userGoal.equals("")) {
            alertDialog(FIELD_IS_EMPTY_MESSAGE);
        }
        else if (userGoal.length() > MAX_SUM_GOAL_LETTERS) {
            alertDialog(TOO_MANY_LETTERS_MESSAGE);
        }
        else {
            chooseWriteRowForFilling(userGoal);
        }
    }

    private void alertDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(USER_BOX_TITLE);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        closeKeyboard();
    }

    private void chooseWriteRowForFilling(String userInputGoal) {
        Boolean result = hasRowForFilling(userInputGoal);
        if (result == null) {
            //Some code
        }
        else {
            //Some code
        }
        closeKeyboard();
    }

    private Boolean hasRowForFilling(String userInputGoal) {
        for (GoalRow goalRow : goalRows) {
            String textGoal = goalRow.getGoalField().getText().toString();
            if (textGoal.equals("")) {
                setNewRow(userInputGoal, goalRow.getGoalField(), goalRow.getGoalSwitch());
                return true;
            }
        }
        return null;
    }

    private void setNewRow(String userInputGoal, TextView userGoal, Switch userSwitch) {
        userGoal.setText(userInputGoal);
        userSwitch.setVisibility(View.VISIBLE);
        userGoal.setVisibility(View.VISIBLE);
        userInput.setText("");
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void hideUnusedEntities(ArrayList<View> entities) {
//        entities.forEach(entity -> entity.setVisibility(View.INVISIBLE));
//    }

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
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
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
        View view = addButton;
        if (view != null) {
            InputMethodManager manager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}