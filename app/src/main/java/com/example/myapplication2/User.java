package com.example.myapplication2;

public class User {
    private String password;
    private String nickName;
    private String email;
    private String userID;
    private int userLevel;
    private int coin;
    private String currentPlant;
    private int currentPlantXP;
    private String profileImage;

    public User() {}

    public User(String userID, String email, String password, String nickName, int userLevel, int coin, String currentPlant, int currentPlantXP, String profileImage ) {
        this.userID = userID;
        this.password = password;
        this.nickName=nickName;
        this.email=email;
        this.userLevel=userLevel;
        this.coin = coin;
        this.currentPlant = currentPlant;
        this.currentPlantXP = currentPlantXP;
        this.profileImage = profileImage;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {return password; }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentPlant() {
        return currentPlant;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public int getCurrentPlantXP() {
        return currentPlantXP;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int c){coin=c;}
}
