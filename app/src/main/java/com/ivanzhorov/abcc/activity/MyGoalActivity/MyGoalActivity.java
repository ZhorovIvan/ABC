package com.ivanzhorov.abcc.activity.MyGoalActivity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ivanzhorov.abcc.R;
import java.util.ArrayList;
import java.util.List;


public class MyGoalActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private int currentApiVersion;
    private Button addButton;
    private TextView goal1, goal2, goal3, goal4, goal5;
    private EditText userInput;
    private List<GoalRow> goalRows;
    private Dialog optionLayoutDialog;
    private Button optionLayoutDoneBtn, optionLayoutChangeBtn;
    private static final int INVISIBLE = 0x00000004;
    private static final String USER_BOX_TITLE = "Информационное сообщение";
    private static final int MAX_SUM_GOAL_LETTERS = 32;
    private static final String FIELD_IS_EMPTY_MESSAGE = "Вы не написали цель";
    private static final String TOO_MANY_LETTERS_MESSAGE = "Ваша цель слишком длинная. Попробуйте сократить";
    private static final String TOO_MANY_ROWS_MESSAGE = "Вы пытаетесь добавить 6 цель. Выполните предыдущие!";

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
    @Override
    protected void onPause() {
        super.onPause();
        saveAllStates();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveAllStates();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStop() {
        super.onStop();
        saveAllStates();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initAllEntities() {
        addButton = findViewById(R.id.add_new_goal);

        goal1 = findViewById(R.id.goal1);
        goal2 = findViewById(R.id.goal2);
        goal3 = findViewById(R.id.goal3);
        goal4 = findViewById(R.id.goal4);
        goal5 = findViewById(R.id.goal5);

        userInput = findViewById(R.id.input_goal);
        userInput.setVisibility(View.INVISIBLE);

        //If it necessary you can add new goal row
        goalRows = new ArrayList(){{
            add(new GoalRow(goal1, "1"));
            add(new GoalRow(goal2, "2"));
            add(new GoalRow(goal3, "3"));
            add(new GoalRow(goal4, "4"));
            add(new GoalRow(goal5, "5"));
        }};

        optionLayoutDialog = new Dialog(this);
    }

    private void setButtonListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushAddButton();
            }
        });

        setGoalOnClickListener(goal1);
        setGoalOnClickListener(goal2);
        setGoalOnClickListener(goal3);
        setGoalOnClickListener(goal4);
        setGoalOnClickListener(goal5);
    }

    private void setGoalOnClickListener(TextView goal) {
        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogEditGoalRow(goal);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveAllStates() {
        SharedPreferences.Editor editPrefs = prefs.edit();
        goalRows.forEach(goalRow -> {
            editPrefs.putString(goalRow.getTextViewValueName(), goalRow.getGoalField().getText().toString());
            editPrefs.putInt(goalRow.getTextViewVisibleStatusName(), goalRow.getGoalField().getVisibility());
        });
        editPrefs.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void restoreAllStates() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        goalRows.forEach(goalRow -> {
            goalRow.getGoalField().setText(prefs.getString(goalRow.getTextViewValueName(), ""));
            goalRow.getGoalField().setVisibility(prefs.getInt(goalRow.getTextViewVisibleStatusName(), INVISIBLE));
        });
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
        if (filledRow != null) {
            String text = filledRow.getGoalField().getText().toString();
            hideUnusualRow(filledRow.getGoalField());
            //Write from filled row to empty
            showRow(emptyRow.getGoalField(), text);
        }
    }

    private void showRow(TextView textView, String text) {
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
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

    private void hideUnusualRow(TextView userGoal) {
        userGoal.setText("");
        userGoal.setVisibility(View.INVISIBLE);
    }

    private void pushAddButton() {
        userInput.setText("");
        userInput.setVisibility(View.VISIBLE);
        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    enterPushed();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isValidateUserInput(String userGoal) {
        if (userGoal.equals("")) {
            alertDialogMessageError(FIELD_IS_EMPTY_MESSAGE);
            return false;
        }
        else if (userGoal.length() > MAX_SUM_GOAL_LETTERS) {
            alertDialogMessageError(TOO_MANY_LETTERS_MESSAGE);
            return false;
        }
        return true;
    }

    private void alertDialogMessageError(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(USER_BOX_TITLE);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void enterPushed() {
        String userGoal = String.valueOf(userInput.getText());
        if (isValidateUserInput(userGoal)) {
            userInput.setVisibility(View.INVISIBLE);
            closeKeyboard();
            if (!hasEmptyRowForFilling(userGoal)) {
                alertDialogMessageError(TOO_MANY_ROWS_MESSAGE);
            }
        }
    }

    private void alertDialogEditGoalRow(TextView goal) {
        optionLayoutDialog.setContentView(R.layout.choose_option_layout);
        optionLayoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionLayoutDoneBtn = optionLayoutDialog.findViewById(R.id.delete_btn);
        optionLayoutChangeBtn = optionLayoutDialog.findViewById(R.id.change_btn);
        optionLayoutDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionLayoutDialog.dismiss();
                pushOptionLayoutDeleteBtn(goal);
            }
        });
        optionLayoutChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionLayoutDialog.dismiss();
                pushOptionLayoutChangeBtn(goal);
            }
        });
        optionLayoutDialog.show();
    }

    private void pushOptionLayoutDeleteBtn(TextView goal) {
        hideUnusualRow(goal);
        sortGoalRows();
    }

    private void pushOptionLayoutChangeBtn(TextView goal) {
        userInput.setVisibility(View.VISIBLE);
        userInput.setText(goal.getText());
        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    enterPushedForOptionLayout(goal);
                    return true;
                }
                return false;
            }
        });
    }

    private void enterPushedForOptionLayout(TextView goal) {
        String userGoal = String.valueOf(userInput.getText());
        if (isValidateUserInput(userGoal)) {
            userInput.setVisibility(View.INVISIBLE);
            goal.setText(userGoal);
            closeKeyboard();
        }
    }

    private boolean hasEmptyRowForFilling(String userInputGoal) {
        for (GoalRow goalRow : goalRows) {
            String textGoal = goalRow.getGoalField().getText().toString();
            if (textGoal.equals("")) {
                setNewRow(userInputGoal, goalRow.getGoalField());
                return true;
            }
        }
        return false;
    }

    private void setNewRow(String userInputGoal, TextView userGoal) {
        userGoal.setText(userInputGoal);
        userGoal.setVisibility(View.VISIBLE);
        userInput.setText("");
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