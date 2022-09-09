package com.ivanzhorov.abcc.activity.MyGoalActivity;

import android.widget.Switch;
import android.widget.TextView;

public class GoalRow {

    private TextView goalField;
    private Switch goalSwitch;

    public GoalRow(TextView goalField, Switch goalSwitch) {
        this.goalField = goalField;
        this.goalSwitch = goalSwitch;
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

}
