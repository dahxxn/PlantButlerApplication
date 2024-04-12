package com.example.myapplication2;


import java.util.ArrayList;

public class ImageList {
    private ArrayList<String> imageList= new ArrayList<>();

    public ImageList() {
    }

    public ImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void addList(String imageName){
        imageList.add(imageName);
    }

    public void deleteList(String imageName) {imageList.remove(imageName); }

    public int arrLength(){
        return imageList.size();
    }


}
