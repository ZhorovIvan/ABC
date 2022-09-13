package com.ivanzhorov.abcc.activity.MyGoalActivity;

import android.widget.Switch;
import android.widget.TextView;

public class GoalRow {

    private TextView goalField;
    private Switch goalSwitch;
    private String TextViewName;
    private String SwitchName;

    public GoalRow(TextView goalField, Switch goalSwitch, String textViewName, String switchName) {
        this.goalField = goalField;
        this.goalSwitch = goalSwitch;
        TextViewName = textViewName;
        SwitchName = switchName;
    }

    public TextView getGoalField() {
        return goalField;
    }

    public void setGoalField(TextView goalField) {
        this.goalField = goalField;
    }

    public Switch getGoalSwitch() {
        return goalSwitch;
    }

    public void setGoalSwitch(Switch goalSwitch) {
        this.goalSwitch = goalSwitch;
    }

    public String getTextViewName() {
        return TextViewName;
    }

    public void setTextViewName(String textViewName) {
        TextViewName = textViewName;
    }

    public String getSwitchName() {
        return SwitchName;
    }

    public void setSwitchName(String switchName) {
        SwitchName = switchName;
    }
}
