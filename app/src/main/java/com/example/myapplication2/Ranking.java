package com.example.myapplication2;

public class Ranking {
    private String gardenID;
    private int gardenScore;
    private String userID;
    private int likeCount;

    public Ranking(){}

    public Ranking(String garden_id, int i, String id, int i1) {
    this.gardenID = garden_id;
    this.gardenScore = i;
    this.userID = id;
    this.likeCount = i1;
    }

    public String getGardenID() {return gardenID; }
    public String getUserID() {return userID;}
    public int getGardenScore(){return gardenScore; }
    public int getLikeCount(){return likeCount; }

}
