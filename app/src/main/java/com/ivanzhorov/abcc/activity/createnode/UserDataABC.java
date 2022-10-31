package com.ivanzhorov.abcc.activity.createnode;
import java.util.ArrayList;

public class UserDataABC {

    private String time;
    private String date;
    private String situation;
    private ArrayList<String> thoughts;
    private ArrayList<Feeling> feelings;
    private ArrayList<String> currentBehaviours;
    private ArrayList<String> yourRationalWayToThings;
    private ArrayList<String> newBehaviors;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public ArrayList<String> getThoughts() {
        return thoughts;
    }

    public void setThoughts(ArrayList<String> thoughts) {
        this.thoughts = thoughts;
    }

    public ArrayList<Feeling> getFeelings() {
        return feelings;
    }

    public void setFeelings(ArrayList<Feeling> feelings) {
        this.feelings = feelings;
    }

    public ArrayList<String> getCurrentBehaviours() {
        return currentBehaviours;
    }

    public void setCurrentBehaviours(ArrayList<String> currentBehaviours) {
        this.currentBehaviours = currentBehaviours;
    }

    public ArrayList<String> getYourRationalWayToThings() {
        return yourRationalWayToThings;
    }

    public void setYourRationalWayToThings(ArrayList<String> yourRationalWayToThings) {
        this.yourRationalWayToThings = yourRationalWayToThings;
    }

    public ArrayList<String> getNewBehaviors() {
        return newBehaviors;
    }

    public void setNewBehaviors(ArrayList<String> newBehaviors) {
        this.newBehaviors = newBehaviors;
    }
}
