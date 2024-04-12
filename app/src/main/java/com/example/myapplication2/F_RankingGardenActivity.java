package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class F_RankingGardenActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String p1, p2, p3, p4, p5, uid, profileurl;
    ImageView plant1, plant2, plant3, plant4, plant5, profile, pot1, pot2, pot3, pot4, pot5;
    TextView pname1, pname2, pname3, pname4, pname5, name;
    ImageView[] box, potbox;
    TextView[] textbox;
    String[] pid;

    Button back_btn;
    public String currentPlantName;
    int currentPlantXP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franking_garden);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        name = (TextView) findViewById(R.id.name);
        back_btn = findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(F_RankingGardenActivity.this, G_Ranking.class);
                startActivity(intent);
            }
        });

        db.collection("Garden")
                .whereEqualTo("userID", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                try {
                                    plant1 = (ImageView) findViewById(R.id.plant1);
                                    plant2 = (ImageView) findViewById(R.id.plant2);
                                    plant3 = (ImageView) findViewById(R.id.plant3);
                                    plant4 = (ImageView) findViewById(R.id.plant4);
                                    plant5 = (ImageView) findViewById(R.id.plant5);
                                    box = new ImageView[]{plant1, plant2, plant3, plant4, plant5};

                                    pname1 = (TextView) findViewById(R.id.plantName1);
                                    pname2 = (TextView) findViewById(R.id.plantName2);
                                    pname3 = (TextView) findViewById(R.id.plantName3);
                                    pname4 = (TextView) findViewById(R.id.plantName4);
                                    pname5 = (TextView) findViewById(R.id.plantName5);
                                    textbox = new TextView[]{pname1, pname2, pname3, pname4, pname5};

                                    pot1 = (ImageView) findViewById(R.id.pot1);
                                    pot2 = (ImageView) findViewById(R.id.pot2);
                                    pot3 = (ImageView) findViewById(R.id.pot3);
                                    pot4 = (ImageView) findViewById(R.id.pot4);
                                    pot5 = (ImageView) findViewById(R.id.pot5);
                                    potbox = new ImageView[]{pot1, pot2, pot3, pot4, pot5};

                                    p1 = document.getData().get("plant1ID").toString();
                                    p2 = document.getData().get("plant2ID").toString();
                                    p3 = document.getData().get("plant3ID").toString();
                                    p4 = document.getData().get("plant4ID").toString();
                                    p5 = document.getData().get("plant5ID").toString();

                                    pid = new String[]{p1, p2, p3, p4, p5};
                                    if (p1.equals(""))
                                        startToast("아직 정원을 등록하지 않은 회원입니다!");
                                    else {


                                        for (int i = 0; i < 5; i++) {
                                            setimage(i);
                                        }


                                    }

                                } catch (Exception e) {
                                    startToast("pid안됨...");
                                }
                            }
                        }
                    }
                });

        db.collection("User")
                .whereEqualTo("userID", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userName = null;

                                try {
                                    userName = document.getData().get("nickName").toString();
                                    name.setText(userName);

                                    if (document.getData().get("profileImage") != null) {
                                        profileurl = document.getData().get("profileImage").toString();
                                        Setting_profile(profileurl);
                                    } else {
                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        StorageReference storageReference = storage.getReference();
                                        StorageReference pathReference;

                                        pathReference = storageReference.child("Empty_Profile.png");
                                        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                profile = (ImageView) findViewById(R.id.profile);
                                                Glide.with(F_RankingGardenActivity.this).load(uri).into(profile);
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    startToast("user안됨...");

                                }
                            }
                        }
                    }
                });


    }

    //메인프로필사진 세팅
    private void Setting_profile(String profileurl) {
        profile = (ImageView) findViewById(R.id.profile);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("Empty_Profile.png");
        if (!profileurl.equals(null)) { //프로파일이미지가 있다면
            pathReference = storageReference.child(uid + "Profile.jpg");
        }
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(F_RankingGardenActivity.this).load(uri).circleCrop().into(profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setimage(int i) {
        db.collection("UserPlant")
                .whereEqualTo("plantID", pid[i])
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    String pname = document.getData().get("plantName").toString();
                                    String ptype = document.getData().get("plantType").toString();
                                    String potType = document.getData().get("potType").toString();

                                    String finalUserPlant = ptype;
                                    String finalUserPlantPot = potType;

                                    db.collection("User").whereEqualTo("userID", uid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        currentPlantName = document.getData().get("currentPlant").toString();
                                                        currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());

                                                        if (pname.equals(currentPlantName)) {
                                                            if (finalUserPlant.equals("몬스테라")) {
                                                                if (currentPlantXP <= 30) {
                                                                    box[i].setImageResource(R.drawable.monstera1);
                                                                } else if (currentPlantXP <= 60) {
                                                                    box[i].setImageResource(R.drawable.monstera2);
                                                                } else {
                                                                    box[i].setImageResource(R.drawable.monstera3);
                                                                }
                                                            } else if (finalUserPlant.equals("해바라기")) {
                                                                if (currentPlantXP <= 30) {
                                                                    box[i].setImageResource(R.drawable.sunflower1);
                                                                } else if (currentPlantXP <= 60) {
                                                                    box[i].setImageResource(R.drawable.sunflower2);
                                                                } else {
                                                                    box[i].setImageResource(R.drawable.sunflower3);
                                                                }
                                                            }
                                                            if (finalUserPlantPot.equals("pot_1")) {
                                                                potbox[i].setImageResource(R.drawable.pot1);

                                                            } else if (finalUserPlantPot.equals("pot_2")) {
                                                                potbox[i].setImageResource(R.drawable.pot2);

                                                            } else if (finalUserPlantPot.equals("pot_3")) {
                                                                potbox[i].setImageResource(R.drawable.pot3);
                                                            }
                                                        } else {
                                                            if (finalUserPlant.equals("몬스테라")) {
                                                                box[i].setImageResource(R.drawable.monstera3);
                                                            } else if (finalUserPlant.equals("해바라기")) {
                                                                box[i].setImageResource(R.drawable.sunflower3);
                                                            }

                                                            if (finalUserPlantPot.equals("pot_1")) {
                                                                potbox[i].setImageResource(R.drawable.pot1);

                                                            } else if (finalUserPlantPot.equals("pot_2")) {
                                                                potbox[i].setImageResource(R.drawable.pot2);

                                                            } else if (finalUserPlantPot.equals("pot_3")) {
                                                                potbox[i].setImageResource(R.drawable.pot3);
                                                            }
                                                        }

                                                        textbox[i].setText(pname);
                                                    }

                                                }
                                            });
                                } catch (Exception e) {
                                    startToast("userPlant안됨...");
                                }
                            }
                        }
                    }
                });
    }

}
