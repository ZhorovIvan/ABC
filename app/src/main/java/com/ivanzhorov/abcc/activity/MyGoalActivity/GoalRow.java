package com.ivanzhorov.abcc.activity.MyGoalActivity;

import android.widget.Switch;
import android.widget.TextView;

public class GoalRow {

    private final TextView goalField;
    private final Switch goalSwitch;
    private final String rowNumber;
    private final String textViewValueName = "textViewValue";
    private final String switchValueName = "switchValue";
    private final String textViewVisibleStatusName = "textViewStatus";
    private final String switchVisibleStatusName = "switchStatus";

    public GoalRow(TextView goalField, Switch goalSwitch, String rowNumber) {
        this.goalField = goalField;
        this.goalSwitch = goalSwitch;
        this.rowNumber = rowNumber;
    }

    public TextView getGoalField() {
        return goalField;
    }

    public Switch getGoalSwitch() {
        return goalSwitch;
    }

    public String getTextViewValueName() {
        return textViewValueName + rowNumber;
    }

    public String getSwitchValueName() {
        return switchValueName + rowNumber;
    }

    public String getTextViewVisibleStatusName() {
        return textViewVisibleStatusName + rowNumber;
    }

    public String getSwitchVisibleStatusName() {
        return switchVisibleStatusName + rowNumber;
    }
}
