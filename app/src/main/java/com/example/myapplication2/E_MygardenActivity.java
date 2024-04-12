package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class E_MygardenActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    ArrayList<String> plantList = new ArrayList<>();
    Button registerBtn, back_btn;
    int currentPlantXP;

    ImageView plant1, plant2, plant3, plant4, plant5;
    ImageView plant1_pot, plant2_pot, plant3_pot, plant4_pot, plant5_pot;
    String p1, p2, p3, p4, p5;
    public String currentPlantName;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emygarden);

        back_btn = findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(E_MygardenActivity.this, D_CommunityMainActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        plant1 = (ImageView) findViewById(R.id.plant1);
        plant2 = (ImageView) findViewById(R.id.plant2);
        plant3 = (ImageView) findViewById(R.id.plant3);
        plant4 = (ImageView) findViewById(R.id.plant4);
        plant5 = (ImageView) findViewById(R.id.plant5);
        ImageView[] box = new ImageView[]{plant1, plant2, plant3, plant4, plant5};

        plant1_pot = (ImageView) findViewById(R.id.plant1_pot);
        plant2_pot = (ImageView) findViewById(R.id.plant2_pot);
        plant3_pot = (ImageView) findViewById(R.id.plant3_pot);
        plant4_pot = (ImageView) findViewById(R.id.plant4_pot);
        plant5_pot = (ImageView) findViewById(R.id.plant5_pot);
        ImageView[] potbox = new ImageView[]{plant1_pot, plant2_pot, plant3_pot, plant4_pot, plant5_pot};

        registerBtn = (Button) findViewById(R.id.registerBtn);

        db.collection("UserPlant")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                View view = (View) getLayoutInflater().
                                        inflate(R.layout.plant, null);
                                GridLayout container = findViewById(R.id.plantLayout);
                                String userPlant = null;
                                String userPlantPot = null;

                                try {
                                    //갖고잇는 식물 보여줌
                                    userPlant = document.getData().get("plantType").toString();
                                    String userPlantName = document.getData().get("plantName").toString();
                                    String plantID = document.getData().get("plantID").toString();
                                    userPlantPot = document.getData().get("potType").toString();

                                    String finalUserPlant = userPlant;
                                    String finalUserPlantPot = userPlantPot;

                                    ImageView plant = (ImageView) view.findViewById(R.id.plant); //식물사진
                                    ImageView pot = (ImageView) view.findViewById(R.id.pot); // 화분사진
                                    TextView plant_nick = (TextView) view.findViewById(R.id.text); //식물이름

                                    db.collection("User").whereEqualTo("userID", user.getUid())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        currentPlantName = document.getData().get("currentPlant").toString();
                                                        currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());

                                                        if (userPlantName.equals(currentPlantName)) {
                                                            if (finalUserPlant.equals("몬스테라")) {
                                                                if (currentPlantXP <= 30) {
                                                                    plant.setImageResource(R.drawable.monstera1);
                                                                } else if (currentPlantXP <= 60) {
                                                                    plant.setImageResource(R.drawable.monstera2);
                                                                } else {
                                                                    plant.setImageResource(R.drawable.monstera3);
                                                                }
                                                            } else if (finalUserPlant.equals("해바라기")) {
                                                                if (currentPlantXP <= 30) {
                                                                    plant.setImageResource(R.drawable.sunflower1);
                                                                } else if (currentPlantXP <= 60) {
                                                                    plant.setImageResource(R.drawable.sunflower2);
                                                                } else {
                                                                    plant.setImageResource(R.drawable.sunflower3);
                                                                }
                                                            }
                                                            if (finalUserPlantPot.equals("pot_1")) {
                                                                pot.setImageResource(R.drawable.pot1);

                                                            } else if (finalUserPlantPot.equals("pot_2")) {
                                                                pot.setImageResource(R.drawable.pot2);

                                                            } else if (finalUserPlantPot.equals("pot_3")) {
                                                                pot.setImageResource(R.drawable.pot3);
                                                            }
                                                        } else {
                                                            if (finalUserPlant.equals("몬스테라")) {
                                                                plant.setImageResource(R.drawable.monstera3);
                                                            } else if (finalUserPlant.equals("해바라기")) {
                                                                plant.setImageResource(R.drawable.sunflower3);
                                                            }

                                                            if (finalUserPlantPot.equals("pot_1")) {
                                                                pot.setImageResource(R.drawable.pot1);

                                                            } else if (finalUserPlantPot.equals("pot_2")) {
                                                                pot.setImageResource(R.drawable.pot2);

                                                            } else if (finalUserPlantPot.equals("pot_3")) {
                                                                pot.setImageResource(R.drawable.pot3);
                                                            }
                                                        }

                                                    }

                                                }
                                            });

                                    plant_nick.setText(userPlantName);
                                    container.addView(view);

                                    ///식물 누르면 정원에 올라감
                                    plant.setClickable(true);
                                    plant.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            try {
                                                BitmapDrawable bitmapPlant = (BitmapDrawable) plant.getDrawable();
                                                Bitmap plantBitmap = bitmapPlant.getBitmap();
                                                box[i].setImageBitmap(plantBitmap);
                                                plantList.add(plantID);
                                            } catch (Exception e) {
                                                startToast("식물 올릴수 엇ㅂ음");
                                            }

                                            try {

                                                BitmapDrawable bitmapPot = (BitmapDrawable) pot.getDrawable();
                                                Bitmap potBitmap = bitmapPot.getBitmap();

                                                potbox[i].setImageBitmap(potBitmap);
                                            }catch (Exception e){
                                                startToast("화분 올릴수업음");
                                            }

                                            i++;

                                        }
                                    });


                                } catch (Exception e) {
                                    Toast.makeText(E_MygardenActivity.this, "못가져옵니다...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    p1 = plantList.get(0);
                    p2 = plantList.get(1);
                    p3 = plantList.get(2);
                    p4 = plantList.get(3);
                    p5 = plantList.get(4);

                    String uid = user.getUid();
                    String garden_id = uid.concat("_garden");

                    DocumentReference userRef = db.collection("Garden").document(uid);
                    userRef.delete();

                    Garden garden = new Garden(garden_id, uid, p1, p2, p3, p4, p5);


                    db.collection("Garden")
                            .whereEqualTo("userID", user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String plant1ID;
                                            try {

                                                document.getReference().delete();


                                            } catch (Exception e) {
                                                startToast("실패...");
                                            }
                                        }

                                        db.collection("Garden").add(garden);
                                        startToast("정원이 등록됐어요!");
                                    }
                                }
                            });

                    Intent intent = new Intent(getApplicationContext(), D_CommunityMainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    startToast("식물을 다섯 개 골라 주세요!");
                }

            }


        });


    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}