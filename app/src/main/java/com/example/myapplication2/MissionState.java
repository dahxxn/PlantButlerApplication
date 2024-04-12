package com.example.myapplication2;

public class MissionState {
    private String userID;
    private boolean daily_3000;
    private boolean daily_4000;
    private boolean daily_5000;
    private boolean daily_10000;
    private boolean daily_15000;
    private boolean daily_30000;
    private boolean daily_50000;

    boolean week_30000;
    boolean week_50000;
    boolean week_70000;

    public MissionState(){

    }

    MissionState(String userID){
        this.userID = userID;
        this.daily_3000 = false;
        this.daily_4000 = false;
        this.daily_5000 = false;
        this.daily_10000 = false;
        this.daily_15000 = false;
        this.daily_30000 = false;
        this.daily_50000 = false;

        this.week_30000 = false;
        this.week_50000 = false;
        this.week_70000 = false;
    }


    public String getUserID(){return userID; }
    public boolean getDaily_3000() {return daily_3000; }
    public boolean getDaily_4000() {return daily_4000; }
    public boolean getDaily_5000() {return daily_5000; }
    public boolean getDaily_10000() {return daily_10000; }
    public boolean getDaily_15000() {return daily_15000; }
    public boolean getDaily_30000() {return daily_30000; }
    public boolean getDaily_50000() {return daily_50000; }


    public boolean getWeek_30000() {return week_30000; }
    public boolean getWeek_50000() {return week_50000; }
    public boolean getWeek_70000() {return week_70000; }


}
