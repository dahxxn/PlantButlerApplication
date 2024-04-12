//
//package com.example.myapplication2;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//public class D_MyPageActivity extends AppCompatActivity {
//
//    ImageView currentPlant_btn,currentPlant_pot;
//    TextView currentPlant_name;
//    public String currentPlantName,currentPlantType,currentPlantPot;
//    int currentPlantXP;
//
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private FirebaseAuth mAuth;
//    private FirebaseUser user ;
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_d_mypage);
//
//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
//
//        //이전 버튼 추가
//        Button backBtn = (Button) findViewById(R.id.backBtn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), C_MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        currentPlant_btn = (ImageView) findViewById(R.id.mypage_currentPlant);
//        currentPlant_pot = (ImageView)findViewById(R.id.mypage_currentPlant_pot);
//
//        db.collection("User")
//                .whereEqualTo("userID", user.getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot document : task.getResult()){
//                                currentPlantName = document.getData().get("currentPlant").toString();
//                                currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());
//
//                                if(!currentPlantName.equals("")){
//                                    currentPlant_name = (TextView) findViewById(R.id.mypage_currentPlantName);
//                                    currentPlant_name.setText(currentPlantName);
//                                    db.collection("UserPlant")
//                                            .whereEqualTo("userID", user.getUid())
//                                            .whereEqualTo("plantName", currentPlantName)
//                                            .get()
//                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                    if(task.isSuccessful()){
//                                                        for(QueryDocumentSnapshot document : task.getResult()){
//                                                            currentPlantPot = document.getData().get("potType").toString();
//                                                            currentPlantType = document.getData().get("plantType").toString();
//
//                                                            if(currentPlantType.equals("몬스테라")){
//                                                                if(currentPlantXP <=30){
//                                                                    currentPlant_btn.setImageResource(R.drawable.monstera1);
//                                                                }else if(currentPlantXP <=60){
//                                                                    currentPlant_btn.setImageResource(R.drawable.monstera2);
//                                                                }else{
//                                                                    currentPlant_btn.setImageResource(R.drawable.monstera3);
//                                                                }
//                                                            }else if(currentPlantType.equals("해바라기")){
//                                                                if(currentPlantXP <=30){
//                                                                    currentPlant_btn.setImageResource(R.drawable.sunflower1);
//                                                                }else if(currentPlantXP <=60){
//                                                                    currentPlant_btn.setImageResource(R.drawable.sunflower2);
//                                                                }else{
//                                                                    currentPlant_btn.setImageResource(R.drawable.sunflower3);
//                                                                }                                                            }
//
//
//                                                            if(currentPlantPot.equals("pot_1")){
//                                                                currentPlant_pot.setImageResource(R.drawable.pot1);
//
//                                                            }else if(currentPlantPot.equals("pot_2")){
//                                                                currentPlant_pot.setImageResource(R.drawable.pot2);
//
//                                                            }else if(currentPlantPot.equals("pot_3")){
//                                                                currentPlant_pot.setImageResource(R.drawable.pot3);
//                                                            }
//
//                                                        }
//                                                    }
//                                                }
//                                            });
//                                }
//                            }
//                        }
//                    }
//                });
//
//        db.collection("UserPlant")
//                .whereEqualTo("userID", user.getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            int i = 0;
//
//                            int plant_id[] = {R.id.plant1, R.id.plant2, R.id.plant3, R.id.plant4, R.id.plant5, R.id.plant6};
//                            int plant_pot_id[] = {R.id.plant1_pot, R.id.plant2_pot, R.id.plant3_pot, R.id.plant4_pot, R.id.plant5_pot, R.id.plant6_pot};
//
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                ImageView plant = (ImageView) findViewById(plant_id[i]);
//                                ImageView plant_pot = (ImageView) findViewById(plant_pot_id[i]);
//
//                                String userPlant = null;
//                                String userPlantPot = null;
//
//                                try {
//                                    String userPlantName = document.getData().get("plantName").toString();
//                                    userPlant = document.getData().get("plantType").toString();
//                                    userPlantPot = document.getData().get("potType").toString();
//
//                                    String finalUserPlant = userPlant;
//                                    String finalUserPlantPot = userPlantPot;
//                                    db.collection("User").whereEqualTo("userID",user.getUid())
//                                            .get()
//                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                    for(QueryDocumentSnapshot document : task.getResult()){
//                                                        currentPlantName = document.getData().get("currentPlant").toString();
//                                                        currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());
//
//                                                        if(userPlantName.equals(currentPlantName)){
//                                                            if (finalUserPlant.equals("몬스테라")) {
//                                                                if(currentPlantXP<=30){
//                                                                    plant.setImageResource(R.drawable.monstera1);
//                                                                }else if(currentPlantXP <=60){
//                                                                    plant.setImageResource(R.drawable.monstera2);
//                                                                }else{
//                                                                    plant.setImageResource(R.drawable.monstera3);
//                                                                }
//                                                            }
//                                                            else if (finalUserPlant.equals("해바라기")){
//                                                                if(currentPlantXP<=30){
//                                                                    plant.setImageResource(R.drawable.sunflower1);
//                                                                }else if(currentPlantXP <=60){
//                                                                    plant.setImageResource(R.drawable.sunflower2);
//                                                                }else{
//                                                                    plant.setImageResource(R.drawable.sunflower3);
//                                                                }
//                                                            }
//
//                                                            if(finalUserPlantPot.equals("pot_1")){
//                                                                plant_pot.setImageResource(R.drawable.pot1);
//
//                                                            }else if(finalUserPlantPot.equals("pot_2")){
//                                                                plant_pot.setImageResource(R.drawable.pot2);
//
//                                                            }else if(finalUserPlantPot.equals("pot_3")){
//                                                                plant_pot.setImageResource(R.drawable.pot3);
//                                                            }
//                                                        }else{
//                                                            if (finalUserPlant.equals("몬스테라")) {
//                                                                plant.setImageResource(R.drawable.monstera3);
//                                                            }
//                                                            else if (finalUserPlant.equals("해바라기")){
//                                                                plant.setImageResource(R.drawable.sunflower3);
//                                                            }
//
//                                                            if(finalUserPlantPot.equals("pot_1")){
//                                                                plant_pot.setImageResource(R.drawable.pot1);
//
//                                                            }else if(finalUserPlantPot.equals("pot_2")){
//                                                                plant_pot.setImageResource(R.drawable.pot2);
//
//                                                            }else if(finalUserPlantPot.equals("pot_3")){
//                                                                plant_pot.setImageResource(R.drawable.pot3);
//                                                            }
//
//                                                            plant.setOnClickListener(new View.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(View view) {
//                                                                }
//                                                            });
//                                                        }
//
//                                                    }
//
//                                                }
//                                            });
//
//                                } catch (Exception e) {
//                                    Toast.makeText(D_MyPageActivity.this, "못가져옵니다...", Toast.LENGTH_SHORT).show();
//                                }
//                                i++;
//
//                            }
//                        }
//                    }
//                });
//    }
//
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        Intent intent = new Intent (this , C_MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//}


package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class D_MyPageActivity extends AppCompatActivity {

    private ImageView currentPlant_btn,currentPlant_pot;
    private TextView currentPlant_name;
    private String currentPlantName,currentPlantType,currentPlantPot;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int currentPlantXP;

    private FirebaseAuth mAuth;
    private FirebaseUser user ;
    //public static String select_custom_plantID;

    public int plant_id[] = {R.id.plant1, R.id.plant2, R.id.plant3, R.id.plant4, R.id.plant5, R.id.plant6};
    public int plant_pot_id[] = {R.id.plant1_pot, R.id.plant2_pot, R.id.plant3_pot, R.id.plant4_pot, R.id.plant5_pot, R.id.plant6_pot};
    public ArrayList<String> plant_id_list = new ArrayList<>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_mypage);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //이전 버튼 추가
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), C_MainActivity.class);
                startActivity(intent);
            }
        });

        currentPlant_btn = (ImageView) findViewById(R.id.mypage_currentPlant);
        currentPlant_pot = (ImageView)findViewById(R.id.mypage_currentPlant_pot);

        db.collection("User")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    public void onComplete(Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                currentPlantName = document.getData().get("currentPlant").toString();
                                currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());

                                if(!currentPlantName.equals("")){
                                    currentPlant_name = (TextView) findViewById(R.id.register_currentPlantName);
                                    currentPlant_name.setText(currentPlantName);
                                    db.collection("UserPlant")
                                            .whereEqualTo("userID", user.getUid())
                                            .whereEqualTo("plantName", currentPlantName)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        for(QueryDocumentSnapshot document : task.getResult()){
                                                            currentPlantPot = document.getData().get("potType").toString();
                                                            currentPlantType = document.getData().get("plantType").toString();

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
                                                                }                                                            }


                                                            if(currentPlantPot.equals("pot_1")){
                                                                currentPlant_pot.setImageResource(R.drawable.pot1);

                                                            }else if(currentPlantPot.equals("pot_2")){
                                                                currentPlant_pot.setImageResource(R.drawable.pot2);

                                                            }else if(currentPlantPot.equals("pot_3")){
                                                                currentPlant_pot.setImageResource(R.drawable.pot3);
                                                            }

                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });

        db.collection("UserPlant")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;



                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ImageView plant = (ImageView) findViewById(plant_id[i]);
                                ImageView plant_pot = (ImageView) findViewById(plant_pot_id[i]);

                                String userPlant = null;
                                String userPlantPot = null;

                                try {
                                    String userPlantName = document.getData().get("plantName").toString();
                                    userPlant = document.getData().get("plantType").toString();
                                    userPlantPot = document.getData().get("potType").toString();
                                    plant_id_list.add(document.getData().get("plantID").toString());


                                    String finalUserPlant = userPlant;
                                    String finalUserPlantPot = userPlantPot;
                                    db.collection("User").whereEqualTo("userID",user.getUid())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for(QueryDocumentSnapshot document : task.getResult()){
                                                        currentPlantName = document.getData().get("currentPlant").toString();
                                                        currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());

                                                        if(userPlantName.equals(currentPlantName)){
                                                            if (finalUserPlant.equals("몬스테라")) {
                                                                if(currentPlantXP<=30){
                                                                    plant.setImageResource(R.drawable.monstera1);
                                                                }else if(currentPlantXP <=60){
                                                                    plant.setImageResource(R.drawable.monstera2);
                                                                }else{
                                                                    plant.setImageResource(R.drawable.monstera3);
                                                                }
                                                            }
                                                            else if (finalUserPlant.equals("해바라기")){
                                                                if(currentPlantXP<=30){
                                                                    plant.setImageResource(R.drawable.sunflower1);
                                                                }else if(currentPlantXP <=60){
                                                                    plant.setImageResource(R.drawable.sunflower2);
                                                                }else{
                                                                    plant.setImageResource(R.drawable.sunflower3);
                                                                }
                                                            }

                                                            if(finalUserPlantPot.equals("pot_1")){
                                                                plant_pot.setImageResource(R.drawable.pot1);

                                                            }else if(finalUserPlantPot.equals("pot_2")){
                                                                plant_pot.setImageResource(R.drawable.pot2);

                                                            }else if(finalUserPlantPot.equals("pot_3")){
                                                                plant_pot.setImageResource(R.drawable.pot3);
                                                            }
                                                        }else{
                                                            if (finalUserPlant.equals("몬스테라")) {
                                                                plant.setImageResource(R.drawable.monstera3);
                                                            }
                                                            else if (finalUserPlant.equals("해바라기")){
                                                                plant.setImageResource(R.drawable.sunflower3);
                                                            }

                                                            if(finalUserPlantPot.equals("pot_1")){
                                                                plant_pot.setImageResource(R.drawable.pot1);

                                                            }else if(finalUserPlantPot.equals("pot_2")){
                                                                plant_pot.setImageResource(R.drawable.pot2);

                                                            }else if(finalUserPlantPot.equals("pot_3")){
                                                                plant_pot.setImageResource(R.drawable.pot3);
                                                            }

                                                            plant.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                }
                                                            });
                                                        }

                                                    }

                                                }
                                            });

                                } catch (Exception e) {
                                }
                                i++;

                            }
                        }
                    }
                });

        //ImageButton custom_btn = (ImageButton) findViewById(R.id.mypage_currentPlant);
        currentPlant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(D_MyPageActivity.this, E_MyPage_itemActivity.class);
                intent.putExtra("plantName",currentPlantName);
                startActivity(intent);
                finish();
            }
        });

        //ImageButton custom2_btn = (ImageButton) findViewById(R.id.mypage_currentPlant_pot);
        currentPlant_pot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(D_MyPageActivity.this, E_MyPage_itemActivity.class);
                intent.putExtra("plantName",currentPlantName);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plant1:
                intentChange(plant_id_list.get(0));
                break;
            case R.id.plant1_pot:
                intentChange(plant_id_list.get(0));
                break;
            case R.id.plant2:
                intentChange(plant_id_list.get(1));
                break;
            case R.id.plant2_pot:
                intentChange(plant_id_list.get(1));
                break;
            case R.id.plant3:
                intentChange(plant_id_list.get(2));
                break;
            case R.id.plant3_pot:
                intentChange(plant_id_list.get(2));
                break;
            case R.id.plant4:
                intentChange(plant_id_list.get(3));
                break;
            case R.id.plant4_pot:
                intentChange(plant_id_list.get(3));
                break;
            case R.id.plant5:
                intentChange(plant_id_list.get(4));
                break;
            case R.id.plant5_pot:
                intentChange(plant_id_list.get(4));
                break;
            case R.id.plant6:
                intentChange(plant_id_list.get(5));
                break;
            case R.id.plant6_pot:
                intentChange(plant_id_list.get(5));
                break;
        }
    };

    public void intentChange(String plant_id){
        //select_custom_plantID=plant_id;
       // startToast(plant_id);
        Intent intent = new Intent(D_MyPageActivity.this, E_MyPage_customActivity.class);
        intent.putExtra("plantID",plant_id);
        startActivity(intent);
        finish();
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , C_MainActivity.class);
        startActivity(intent);
        finish();
    }

}