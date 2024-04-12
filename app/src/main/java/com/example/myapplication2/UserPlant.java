package com.example.myapplication2;

public class UserPlant {
    private String plantID;
    private String userID;
    private String plantType;
    private String plantName;
    private String potType;

    public UserPlant(String plantID, String userID, String plantType, String plantName, String potType){
        this.plantID=plantID;
        this.userID=userID;
        this.plantType=plantType;
        this.plantName = plantName;
        this.potType = potType;
    }

    public String getUserID() {return userID;}
    public String getPlantID(){return plantID; }
    public String getPlantType(){return plantType;}
    public String getPlantName(){return plantName; }
    public String getPotType(){return potType; }

}