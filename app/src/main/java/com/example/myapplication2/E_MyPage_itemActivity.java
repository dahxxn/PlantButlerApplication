
package com.example.myapplication2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class E_MyPage_itemActivity extends AppCompatActivity {

    private ImageView currentPlant_btn,currentPlant_pot;
    private TextView currentPlant_name, itemText;
    private Dialog dialog1, dialog2;
    private TextView textView1, textView2;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private UserItem userItem = new UserItem(user.getUid());
    private User userInfo;
    private ImageView itemImageView;
    //private int potbox[]=new int[6];
    private int setPotNum;
    private int item_rid[] = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6};
    private String currentPlantName,currentPlantType,currentPlantPot;
    private String plantID;
    private HashMap<String, Integer> item_list;
    private String filedName;
    private String item_id;
    private int itemCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_mypage_item);
        db.collection("User")
                .whereEqualTo("userID",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                currentPlantName = document.getData().get("currentPlant").toString();

                                plantID = user.getUid().concat(currentPlantName);

                                //이전 버튼 추가
                                Button backBtn = (Button) findViewById(R.id.backBtn);
                                backBtn.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(), D_MyPageActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                currentPlant_btn = (ImageView) findViewById(R.id.mypage_currentPlant);
                                currentPlant_pot = (ImageView) findViewById(R.id.mypage_currentPlant_pot);

                                //startToast(plantID);

                                db.collection("UserPlant")
                                        .whereEqualTo("userID", user.getUid())
                                        .whereEqualTo("plantID", plantID)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for(QueryDocumentSnapshot document : task.getResult()) {
                                                        currentPlantName = document.getData().get("plantName").toString();
                                                        currentPlantType = document.getData().get("plantType").toString();
                                                        currentPlantPot = document.getData().get("potType").toString();
                                                    }
                                                    currentPlant_name = (TextView) findViewById(R.id.plantName);
                                                    currentPlant_name.setText(currentPlantName);

                                                    db.collection("User")
                                                            .whereEqualTo("userID", user.getUid())
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if(task.isSuccessful()){
                                                                        for(QueryDocumentSnapshot document : task.getResult()){
                                                                            String real_currentPlantName = document.getData().get("currentPlant").toString();
                                                                            int currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());

                                                                            if(real_currentPlantName.equals(currentPlantName)){
                                                                                if(currentPlantType.equals("몬스테라")){
                                                                                    if(currentPlantXP <=30){
                                                                                        currentPlant_btn.setImageResource(R.drawable.monstera1);
                                                                                    }else if(currentPlantXP <=60){
                                                                                        currentPlant_btn.setImageResource(R.drawable.monstera2);
                                                                                    }else{
                                                                                        currentPlant_btn.setImageResource(R.drawable.monstera3);
                                                                                    }
                                                                                }else if(currentPlantType.equals("해바라기")){
                                                                                    if(currentPlantXP <=30){
                                                                                        currentPlant_btn.setImageResource(R.drawable.sunflower1);
                                                                                    }else if(currentPlantXP <=60){
                                                                                        currentPlant_btn.setImageResource(R.drawable.sunflower2);
                                                                                    }else{
                                                                                        currentPlant_btn.setImageResource(R.drawable.sunflower3);
                                                                                    }
                                                                                }

                                                                            }else{
                                                                                if (currentPlantType.equals("몬스테라")) {
                                                                                    currentPlant_btn.setImageResource(R.drawable.monstera3);
                                                                                } else if (currentPlantType.equals("해바라기")) {
                                                                                    currentPlant_btn.setImageResource(R.drawable.sunflower3);
                                                                                }
                                                                            }

                                                                            if (currentPlantPot.equals("pot_1")) {
                                                                                currentPlant_pot.setImageResource(R.drawable.pot1);
                                                                            } else if (currentPlantPot.equals("pot_2")) {
                                                                                currentPlant_pot.setImageResource(R.drawable.pot2);
                                                                            } else if (currentPlantPot.equals("pot_3")) {
                                                                                currentPlant_pot.setImageResource(R.drawable.pot3);
                                                                            }
                                                                        }
                                                                    }

                                                                }
                                                            });

                                                }
                                            }
                                        });

                                itemActivity(); //userItem 객체 업데이트

                                dialog1 = new Dialog(E_MyPage_itemActivity.this);
                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog1.setCancelable(false);
                                dialog1.setContentView(R.layout.activity_f_mypage_item_popup);

                                //User 객체 업데이트
                                DocumentReference docRef1 = db.collection("User").document(user.getUid());
                                docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        userInfo= documentSnapshot.toObject(User.class);
                                        TextView plant_xp= (TextView) findViewById(R.id.plantXP);
                                        plant_xp.setText(String.valueOf(userInfo.getCurrentPlantXP()));
                                    }
                                });
                            }
                        }
                    }
                });

//        currentPlantName = getIntent().getStringExtra("plantName");




    }

    public void itemActivity() {
        if (user != null) { //로그인된 사용자 있음
            //UserItem 객체 업데이트
            DocumentReference docRef2 = db.collection("UserItem").document(user.getUid());
            docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userItem = documentSnapshot.toObject(UserItem.class);
                    item_list = userItem.getMap_itemCount();
                    //구매된 아이템 리스트 이미지 띄우기
                    int i=0;
                    for (String key :item_list.keySet()) {
                        String itemID=key;
                        itemImageView = (ImageView) findViewById(item_rid[i++]);
                        resourceImage(itemID);
                    }
                }
            });
        } else { //로그인된 사용자 없음
            startToast("No user is signed in");
        }


    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item1:
                useItem(0);
                break;
            case R.id.item2:
                useItem(1);
                break;
            case R.id.item3:
                useItem(2);
                break;
            case R.id.item4:
                useItem(3);
                break;
            case R.id.item5:
                useItem(4);
                break;
            case R.id.item6:
                useItem(5);
                break;

        }
    };

    public void useItem(int i){ //아이템 사용하기

        //선택된 아이템 개수 + 선택된 아이템 이름
        int j=0;
        for(Map.Entry<String, Integer> elem : item_list.entrySet()){
            item_id = elem.getKey();
            itemCount = elem.getValue();
            if(j==i)
                break;
            else{
                j++;
            }
        }

        //firebase UserItem Filed 설정
        if(item_id.equals("dirt_1")||item_id.equals("dirt_2")||item_id.equals("dirt_3")||item_id.equals("dirt_4")||item_id.equals("dirt_5")||item_id.equals("dirt_6")){
            filedName="map_dirtID";
        }
        else{
            filedName="map_nutrientID";
        }


        //아이템 사용하시겠습니까 popup 띄우기
        if(itemCount>0){
            dialog1.show();
            //사용자가 아니요 버튼 누를 때 동작없음
            Button no_btn = dialog1.findViewById(R.id.nobtn);
            no_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.dismiss();
                }
            });


            //ui에 식물의 성장도 증가되게 출력
            //firebase에 식물의 성장도 증가 update
            Button yes_btn = dialog1.findViewById(R.id.yesbtn);
            yes_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemCount--;
                    //UserItem: 아이템 개수 업데이트 + firebase update
                    if(filedName.equals("map_nutrientID"))
                        userItem.increase_nutrientItem(item_id, itemCount);
                    else{
                        userItem.increase_dirtItem(item_id, itemCount);
                    }

                    userItem.decrease_itemCount (item_id, itemCount);
                    db.collection("UserItem").document(user.getUid())
                            .set(userItem);

                    if(itemCount>0){
                        //ui 재설정
                        itemActivity();
                    }
                    else{
                        //ui 재설정
                        itemActivity();
                        itemImageView = (ImageView) findViewById(item_rid[item_list.size()]);
                        itemImageView.setImageResource(R.drawable.space);
                    }

                    if(item_id.equals("dirt_1")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),5);

                        effectItem("생명력 +5", 5);
                    }
                    else if(item_id.equals("dirt_2")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),10);
                        effectItem("생명력 +10", 10);
                    }
                    else if(item_id.equals("dirt_3")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),15);
                        effectItem("생명력 +15", 15);
                    }
                    else if(item_id.equals("dirt_4")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),20);
                        effectItem("생명력 +20", 20);
                    }
                    else if(item_id.equals("dirt_5")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),25);
                        effectItem("생명력 +25", 25);
                    }
                    else if(item_id.equals("dirt_6")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),30);
                        effectItem("생명력 +30", 30);
                    }
                    else if(item_id.equals("nutrient_1")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),5);
                        effectItem("생명력 +5", 5);
                    }
                    else if(item_id.equals("nutrient_2")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),10);
                        effectItem("생명력 +10", 10);
                    }
                    else if(item_id.equals("nutrient_3")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),15);
                        effectItem("생명력 +15", 15);
                    }
                    else if(item_id.equals("nutrient_4")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),20);
                        effectItem("생명력 +20", 20);
                    }
                    else if(item_id.equals("nutrient_5")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),25);
                        effectItem("생명력 +25", 25);
                    }
                    else if(item_id.equals("nutrient_6")){
                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                        mpxp.add_XP(user.getUid(),30);
                        effectItem("생명력 +30", 30);
                    }



                }
            });

        }
    }

    public void effectItem(String effect, int xp){
        startToast(effect);

        //User 객체 업데이트
        DocumentReference docRef1 = db.collection("User").document(user.getUid());
        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userInfo= documentSnapshot.toObject(User.class);
                TextView plant_xp= (TextView) findViewById(R.id.plantXP);
                plant_xp.setText(String.valueOf(userInfo.getCurrentPlantXP()));
            }
        });

        Intent intent = new Intent(E_MyPage_itemActivity.this, E_MyPage_itemActivity.class);
        startActivity(intent);
        dialog1.dismiss();

    }
    public void resourceImage(String itemID){
        if(itemID.equals("dirt_1")){
            itemImageView.setImageResource(R.drawable.item_lifedirt_3);
        }
        else if(itemID.equals("dirt_2")){
            itemImageView.setImageResource(R.drawable.item_lifedirt_2);
        }
        else if(itemID.equals("dirt_3")){
            itemImageView.setImageResource(R.drawable.item_lifedirt_1);
        }
        else if(itemID.equals("dirt_4")){
            itemImageView.setImageResource(R.drawable.item_ddirt_3);
        }
        else if(itemID.equals("dirt_5")){
            itemImageView.setImageResource(R.drawable.item_ddirt_2);
        }
        else if(itemID.equals("dirt_6")){
            itemImageView.setImageResource(R.drawable.item_ddirt_1);
        }
        if(itemID.equals("nutrient_1")){
            itemImageView.setImageResource(R.drawable.item_nutrient_3);
        }
        else if(itemID.equals("nutrient_2")){
            itemImageView.setImageResource(R.drawable.item_nutrient_2);
        }
        else if(itemID.equals("nutrient_3")){
            itemImageView.setImageResource(R.drawable.item_nutrient_1);
        }
        else if(itemID.equals("nutrient_4")){
            itemImageView.setImageResource(R.drawable.item_recover_3);
        }
        else if(itemID.equals("nutrient_5")){
            itemImageView.setImageResource(R.drawable.item_recover_2);
        }
        else if(itemID.equals("nutrient_6")){
            itemImageView.setImageResource(R.drawable.item_recover_1);
        }
    }

    public void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , D_MyPageActivity.class);
        startActivity(intent);
        finish();
    }

}