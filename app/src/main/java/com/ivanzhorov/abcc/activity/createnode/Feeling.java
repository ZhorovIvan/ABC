package com.ivanzhorov.abcc.activity.createnode;

public class Feeling {

    private String feeling;
    private int powerOfFeeling;

    public Feeling(String feeling, int powerOfFeeling) {
        this.feeling = feeling;
        this.powerOfFeeling = powerOfFeeling;
    }

    public String getFeeling() {
        return feeling;
    }

    public int getPowerOfFeeling() {
        return powerOfFeeling;
    }
}
