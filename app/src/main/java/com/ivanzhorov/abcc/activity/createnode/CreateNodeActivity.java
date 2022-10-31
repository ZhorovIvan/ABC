package com.ivanzhorov.abcc.activity.createnode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.ivanzhorov.abcc.R;

public class CreateNodeActivity extends AppCompatActivity {

    private int currentApiVersion;
    private static Dialog dateSettingDialog, timeSettingDialog;
    private static FragmentManager fragmentManager;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static UserDataABC userDataABC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_node);
        hideStatusBar();
        hideSystemButtons();
        userDataABC = new UserDataABC();
        fragmentManager = getSupportFragmentManager();
        context = this;
        getUserTime();
    }

    public static void getUserTime() {
        timeSettingDialog = new Dialog(context);
        timeSettingDialog.setContentView(R.layout.choose_time);
        timeSettingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button timeSettingButton = timeSettingDialog.findViewById(R.id.choose_time_btn);
        timeSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSettingDialog.dismiss();
                DialogFragment TimePickerFragment = new TimePickerFragment();
                TimePickerFragment.show(fragmentManager, "timePicker");
            }
        });
        timeSettingDialog.show();
    }

    public static void getUserDate() {
        dateSettingDialog = new Dialog(context);
        dateSettingDialog.setContentView(R.layout.choose_date);
        dateSettingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dateSettingButton = dateSettingDialog.findViewById(R.id.choose_date_btn);
        dateSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSettingDialog.dismiss();
                DialogFragment DatePickerFragment = new DatePickerFragment();
                DatePickerFragment.show(fragmentManager, "timePicker");
            }
        });
        dateSettingDialog.show();
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
            decorView
            .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
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
}