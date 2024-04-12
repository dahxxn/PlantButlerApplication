package com.example.myapplication2;
import android.net.Uri;
public class ItemInfo {

    private String dirtID[]={"dirt_1","dirt_2","dirt_3","dirt_4","dirt_5","dirt_6"};
    private String dirtName[]={"3등급 생명흙","2등급 생명흙","1등급 생명흙","3등급 디럭스흙","2등급 디럭스흙","1등급 디럭스흙"};
    private String dirtEffect[]={"생명력 +5", "생명력 +15", "생명력 +20", "생명력 +20", "생명력 +35", "생명력 +50"};
    private int dirtPrice[]={10,15,20,25,30,40};

    private String nutrientID[]={"nutrient_1","nutrient_2","nutrient_3","nutrient_4","nutrient_5","nutrient_6"};
    private String nutrientName[]={"3등급 영양제","2등급 영양제","1등급 영양제","3등급 부활제","2등급 부활제","1등급 부활제"};
    private String nutrientEffect[]={"생명력 시간당 +0.1", "생명력 시간당 +0.2", "생명력 시간당 +0.3", "생명력 시간당 +0.5", "생명력 시간당 +0.7", "생명력 시간당 +0.9"};
    private int nutrientPrice[]={10,15,20,25,30,40};

    private String potID[]={"pot_1","pot_2","pot_3","pot_4","pot_5","pot_6"};
    private String potName[]={"기본 화분 1","기본 화분 2","기본 화분 3","세로 무늬 화분 1","세로 무늬 화분 2","세로 무늬 화분 3"};
    private int potPrice[]={10,15,20,25,30,40};

    public ItemInfo() {
    }

    public String getDirtID(int tmp) {
        return dirtID[tmp];
    }
    public String getDirtName(int tmp) {
        return dirtName[tmp];
    }
    public String getDirtEffect(int tmp) {
        return dirtEffect[tmp];
    }
    public int getDirtPrice(int tmp) {
        return dirtPrice[tmp];
    }

    public String getNutrientID(int tmp) {
        return nutrientID[tmp];
    }
    public String getNutrientName(int tmp) {
        return nutrientName[tmp];
    }
    public String getNutrientEffect(int tmp) {
        return nutrientEffect[tmp];
    }
    public int getNutrientPrice(int tmp) {
        return nutrientPrice[tmp];
    }

    public String getPotID(int tmp) {
        return potID[tmp];
    }
    public String getPotName(int tmp) {
        return potName[tmp];
    }
    public int getPotPrice(int tmp) {
        return potPrice[tmp];
    }

}
