package com.example.myapplication2;

public class Garden {
    private String gardenID;
    private String userID;
    private String plant1ID;
    private String plant2ID;
    private String plant3ID;
    private String plant4ID;
    private String plant5ID;

    public Garden(){}

    public Garden(String gardenID, String userID, String plant1ID, String plant2ID, String plant3ID, String plant4ID, String plant5ID){
        this.gardenID = gardenID;
        this.userID = userID;
        this.plant1ID = plant1ID;
        this.plant2ID = plant2ID;
        this.plant3ID = plant3ID;
        this.plant4ID = plant4ID;
        this.plant5ID = plant5ID;
    }

    public String getGardenID() {return gardenID; }
    public String getUserID() {return userID;}
    public String getPlant1ID() {return plant1ID; }
    public String getPlant2ID(){return plant2ID;}
    public String getPlant3ID(){return plant3ID; }
    public String getPlant4ID(){return plant4ID; }
    public String getPlant5ID(){return plant5ID; }
}
