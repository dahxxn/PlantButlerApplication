package com.example.myapplication2;

public class UserPedometer {
    private String userID;
    private int today_pedometer;
    private int week_pedometer;
    private int month_pedometer;

    public UserPedometer(){}

    public UserPedometer(String userID, int today_pedometer, int week_pedometer, int month_pedometer){
        this.userID = userID;
        this.today_pedometer = today_pedometer;
        this.week_pedometer = week_pedometer;
        this.month_pedometer = month_pedometer;
    }

    public String getUserID(){return userID; }
    public int getToday_pedometer() {return today_pedometer; }
    public int getWeek_pedometer() {return week_pedometer; }
    public int getMonth_pedometer() {return month_pedometer; }


}
