package com.ivanzhorov.abcc.activity.MyGoalActivity;

import android.widget.Switch;
import android.widget.TextView;

public class GoalRow {

    private final TextView goalField;
    private final String rowNumber;
    private final String textViewValueName = "textViewValue";
    private final String textViewVisibleStatusName = "textViewStatus";


    public GoalRow(TextView goalField, String rowNumber) {
        this.goalField = goalField;
        this.rowNumber = rowNumber;
    }

    public TextView getGoalField() {
        return goalField;
    }

    public String getTextViewValueName() {
        return textViewValueName + rowNumber;
    }

    public String getTextViewVisibleStatusName() {
        return textViewVisibleStatusName + rowNumber;
    }

}
