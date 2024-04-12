package com.example.myapplication2;


public class ImageConfirm {
    private String userUid;
    private String imageName;
    private String check;
    private String warningCheck;
    private String warningMessage;

    public ImageConfirm() {
    }

    public ImageConfirm(String userUid, String imageName) {
        this.userUid = userUid;
        this.imageName = imageName;
        this.check = "F";
        this.warningCheck = "F";
        this.warningMessage = "asd";
    }

    public String getUserID() {
        return userUid;
    }

    public String getImageName() {
        return imageName;
    }

    public String getCheck() {
        return check;
    }

    public String getWarningCheck(){return warningCheck;}
    public String getWarningMessage(){return warningMessage;}
}
