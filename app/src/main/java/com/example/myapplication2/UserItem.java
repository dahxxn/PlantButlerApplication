package com.example.myapplication2;

import java.util.HashMap;

public class UserItem {
    private String userID;
    private HashMap<String, Integer> map_dirtID = new HashMap<>();
    private HashMap<String, Integer> map_nutrientID = new HashMap<>();
    private HashMap<String, Boolean> map_potID = new HashMap<>();
    private HashMap<String, Integer> map_itemCount = new HashMap<>();

    public UserItem(){
    }
    public UserItem(String userID){

        this.userID=userID;

        this.map_dirtID.put("dirt_1",0);
        this.map_dirtID.put("dirt_2",0);
        this.map_dirtID.put("dirt_3",0);
        this.map_dirtID.put("dirt_4",0);
        this.map_dirtID.put("dirt_5",0);
        this.map_dirtID.put("dirt_6",0);

        this.map_nutrientID.put("nutrient_1",0);
        this.map_nutrientID.put("nutrient_2",0);
        this.map_nutrientID.put("nutrient_3",0);
        this.map_nutrientID.put("nutrient_4",0);
        this.map_nutrientID.put("nutrient_5",0);
        this.map_nutrientID.put("nutrient_6",0);

        this.map_potID.put("pot_1",false);
        this.map_potID.put("pot_2",false);
        this.map_potID.put("pot_3",false);
        this.map_potID.put("pot_4",false);
        this.map_potID.put("pot_5",false);
        this.map_potID.put("pot_6",false);
    }

    public String getUserID() {
        return userID;
    }

    //map_dirtID
    public HashMap<String, Integer> getMap_dirtID(){
        return map_dirtID;
    }
    public int get_dirtItem_count(String id){
        return map_dirtID.get(id);
    }
    public void increase_dirtItem(String id, int count){
        map_dirtID.put(id,count);
    }

    //map_nutrientID
    public HashMap<String, Integer> getMap_nutrientID(){
        return map_nutrientID;
    }
    public int get_nutrientItem_count(String id){
        return map_nutrientID.get(id);
    }
    public void increase_nutrientItem(String id, int count){
        map_nutrientID.put(id,count);
    }

    //map_potID
    public HashMap<String, Boolean> getMap_potID(){
        return map_potID;
    }
    public Boolean get_potItem_b(String id){
        return map_potID.get(id);
    }
    public void true_potItem(String id){
        map_potID.put(id,true);
    }


    //map_itemCount
    public void increase_itemCount(String id, int count){
        map_itemCount.put(id,count);
    }
    public HashMap<String, Integer> getMap_itemCount(){
        return map_itemCount;
    }
    public void decrease_itemCount (String id, int count){
       if(count>0){
           map_itemCount.put(id, count);
       }
       else{
            map_itemCount.remove(id);
       }
    }
}
