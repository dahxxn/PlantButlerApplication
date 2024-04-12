package com.example.myapplication2;

import static android.widget.Toast.makeText;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class C_MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageButton main_setting, main_mypage, main_community, main_store, main_mission;
    Dialog quizDialog1, quizDialog2, quizDialog3, setting_select_dialog, logout_dialog, quit_dialog;
    private User userInfo;
    private int point, currentxp;
    String currentPlantXP;
    boolean quiz_state;
    private DocumentReference userRef, userPhotoConfirmRef;
    TextView textView2;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    String currentPlantName, profileurl;
    ImageView profile;


    // 추가
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    private int steps = 0;
    private int today_pedometer = 0;
    private int week_pedometer = 0;
    private int month_pedometer = 0;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;
    //////////////////

    //사진인증 변수 추가
    ImageList imageList;
    ArrayList<String> list = new ArrayList<>();
    String Check = "F";
    Dialog PhotoCheck_dialog;
    Button btn;
    //////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //추가
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        /////////////////////////

        //TextView stepCountView = (TextView) findViewById(R.id.stepCountView);

        /*임의로 coin 지정 --나중에 삭제예정-------------
        DocumentReference washingtonRef = db.collection("User").document(user.getUid());
        washingtonRef
                .update("coin",70);*/
        //-------------------------------------------
        userCoin();

        // 화면에 현 걸음 수 출력
        textView2 = (TextView) findViewById(R.id.sensor);
        db.collection("UserPedometer").whereEqualTo("userID", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String working = document.getData().get("today_pedometer").toString();
                        textView2.setText(" " + working);
                    }
                }
            }
        });


        // 사용자가 오늘의 퀴즈 풀었는지 유뮤 확인
        db.collection("QuizState")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                quiz_state = Boolean.parseBoolean(document.getData().get("quiz").toString());

                                if (quiz_state) { //true 이면 이미 푼것
                                    Button main_quiz_btn = (Button) findViewById(R.id.main_quiz_btn);
                                    main_quiz_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(C_MainActivity.this, "이미 풀었습니다!", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                } else {
                                    Button main_quiz_btn = (Button) findViewById(R.id.main_quiz_btn);
                                    main_quiz_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showpopup1();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

        //사용자의 currentPlant 유무 확인 : firebase 데이터베이스 읽기
        db.collection("User")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                currentPlantXP = document.getData().get("currentPlantXP").toString();
                                currentPlantName = document.getData().get("currentPlant").toString();
                                ImageView plant_img = (ImageView) findViewById(R.id.main_plant);
                                ImageView plant_pot_img = (ImageView) findViewById(R.id.main_plant_pot);

                                TextView plant_nick = (TextView) findViewById(R.id.main_plant_nick);
                                TextView plant_xp = (TextView) findViewById(R.id.main_plant_growth);

                                //프로파일사진 url불러오기
                                //프로파일사진 url불러오기
//                                try {
//                                    profileurl = document.getData().get("profileImage").toString();
//                                    Setting_profile(profileurl);
//                                } catch (Exception exception) {
//                                }
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
                                            profile = (ImageView) findViewById(R.id.main_profile);
                                            Glide.with(C_MainActivity.this).load(uri).circleCrop().into(profile);
                                        }
                                    });
                                }

                                if (currentPlantName.equals("")) {
                                    plant_img.setImageResource(R.drawable.plus);
                                    plant_img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(C_MainActivity.this, D_SellectActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {

                                    db.collection("UserPlant")
                                            .whereEqualTo("userID", user.getUid())
                                            .whereEqualTo("plantName", currentPlantName)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            String currentPlantType = document.getData().get("plantType").toString();
                                                            String currentPlantPot = document.getData().get("potType").toString();

                                                            int currentxp = Integer.parseInt(currentPlantXP);
                                                            if (currentPlantType.equals("몬스테라")) {
                                                                if (currentxp <= 30) {
                                                                    plant_img.setImageResource(R.drawable.monstera1);
                                                                } else if (currentxp <= 60) {
                                                                    plant_img.setImageResource(R.drawable.monstera2);
                                                                } else {
                                                                    plant_img.setImageResource(R.drawable.monstera3);
                                                                }
//                                                                plant_img.setImageResource(R.drawable.monstera3);
                                                            } else if (currentPlantType.equals("해바라기")) {
//                                                                plant_img.setImageResource(R.drawable.sunflower3);
                                                                if (currentxp <= 30) {
                                                                    plant_img.setImageResource(R.drawable.sunflower1);
                                                                } else if (currentxp <= 60) {
                                                                    plant_img.setImageResource(R.drawable.sunflower2);
                                                                } else {
                                                                    plant_img.setImageResource(R.drawable.sunflower3);
                                                                }
                                                            }

                                                            if (currentPlantPot.equals("pot_1")) {
                                                                plant_pot_img.setImageResource(R.drawable.pot1);

                                                            } else if (currentPlantPot.equals("pot_2")) {
                                                                plant_pot_img.setImageResource(R.drawable.pot2);

                                                            } else if (currentPlantPot.equals("pot_3")) {
                                                                plant_pot_img.setImageResource(R.drawable.pot3);

                                                            }
                                                        }
                                                    }
                                                }
                                            });


//                                    plant_img.setImageResource(R.drawable.plant1_1);
                                    plant_nick.setText(currentPlantName);
                                    plant_xp.setText(currentPlantXP);
                                }


                            }
                        }
                    }
                });

        //사진 인증 되었을시
        db.collection("ImageConfirm")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userRef = db.collection("ImageConfirm").document(document.getId());
                                Check = document.getData().get("check").toString();
                                String imageName = document.getData().get("imageName").toString();

                                G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                G_ManageCoin mc = new G_ManageCoin();
                                if (Check.equals("T")) {
                                    PhotoCheck_dialog = new Dialog(C_MainActivity.this);
                                    PhotoCheck_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    PhotoCheck_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    PhotoCheck_dialog.setCancelable(false);
                                    PhotoCheck_dialog.setContentView(R.layout.photo_check_dialog);
                                    PhotoCheck_dialog.show();
                                    btn = PhotoCheck_dialog.findViewById(R.id.yesbtn);

                                    mpxp.add_XP(user.getUid(), 1);
                                    mc.add_Coin(user.getUid(), 50);
                                    //ImageConfirm에서 이미지 삭제
                                    userRef.update("check", "F");
                                    userRef.delete();

                                    userRef = db.collection("ImageList").document("list");
                                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            //ImageList에서 이미지 삭제
                                            imageList = new ImageList();
                                            imageList = documentSnapshot.toObject(ImageList.class);
                                            imageList.deleteList(imageName);

                                            db.collection("ImageList").document("list")
                                                    .set(imageList);


                                        }
                                    });

                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PhotoCheck_dialog.dismiss();
                                            Intent intent = new Intent(C_MainActivity.this, C_MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

        //사진 신고 되었을시
        db.collection("ImageConfirm")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                userRef = db.collection("User").document(user.getUid());
                                userPhotoConfirmRef = db.collection("ImageConfirm").document(document.getId());
                                Check = document.getData().get("warningCheck").toString();
                                String imageName = document.getData().get("imageName").toString();

                                G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                G_ManageCoin mc = new G_ManageCoin();

                                if (Check.equals("T")) {
                                    PhotoCheck_dialog = new Dialog(C_MainActivity.this);
                                    PhotoCheck_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    PhotoCheck_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    PhotoCheck_dialog.setCancelable(false);
                                    PhotoCheck_dialog.setContentView(R.layout.declaration_report);
                                    PhotoCheck_dialog.show();
                                    btn = PhotoCheck_dialog.findViewById(R.id.yesbtn);


                                    userRef.update("currentPlantXP", 0);
                                    userRef.update("coin", 0);
                                    //ImageConfirm에서 이미지 정보 삭제
                                    userPhotoConfirmRef.update("warningCheck", "F");
                                    userPhotoConfirmRef.delete();


                                    userRef = db.collection("ImageList").document("list");
                                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            //ImageList에서 이미지 삭제
                                            imageList = new ImageList();
                                            imageList = documentSnapshot.toObject(ImageList.class);
                                            imageList.deleteList(imageName);

                                            db.collection("ImageList").document("list")
                                                    .set(imageList);

                                        }
                                    });


                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PhotoCheck_dialog.dismiss();
                                            Intent intent = new Intent(C_MainActivity.this, C_MainActivity.class);
                                            startActivity(intent);

                                        }
                                    });
                                }
                            }
                        }
                    }
                });
        /////////////////////////////////////////////////////////////////
        setting_select_dialog = new Dialog(C_MainActivity.this);

        setting_select_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setting_select_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setting_select_dialog.setCancelable(true);
        setting_select_dialog.setContentView(R.layout.setting_select_dialog);

        main_setting = (ImageButton) findViewById(R.id.main_setting);
        main_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_setting_popup();
            }
        });

        main_mypage = (ImageButton) findViewById(R.id.main_mypage);
        main_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_MainActivity.this, D_MyPageActivity.class);
                startActivity(intent);
                finish();
            }
        });


        main_community = (ImageButton) findViewById(R.id.main_community);
        main_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_MainActivity.this, D_CommunityMainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        main_store = (ImageButton) findViewById(R.id.main_store);
        main_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_MainActivity.this, D_StoreActivity.class);
                startActivity(intent);
                finish();
            }
        });


        main_mission = (ImageButton) findViewById(R.id.main_mission);
        main_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(C_MainActivity.this, D_MissionActivity.class);
                startActivity(intent);
                finish();
            }
        });


        quizDialog1 = new Dialog(C_MainActivity.this);
        quizDialog2 = new Dialog(C_MainActivity.this);
        quizDialog3 = new Dialog(C_MainActivity.this);

        quizDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quizDialog1.setCancelable(false);
        quizDialog1.setContentView(R.layout.quiz1);

        quizDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quizDialog2.setCancelable(false);
        quizDialog2.setContentView(R.layout.quiz2);

        quizDialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quizDialog3.setCancelable(false);
        quizDialog3.setContentView(R.layout.quiz3);


    }

    //추가
    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    // 이벤트발생!!
                    textView2 = (TextView) findViewById(R.id.sensor);
                    steps += 1;
                    db.collection("UserPedometer").whereEqualTo("userID", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String working = document.getData().get("today_pedometer").toString();

                                    DocumentReference pedoRef = db.collection("UserPedometer").document(user.getUid());
                                    pedoRef.update("today_pedometer", Integer.valueOf(working) + steps);
                                    pedoRef.update("week_pedometer", Integer.valueOf(working) + steps);
                                    pedoRef.update("month_pedometer", Integer.valueOf(working) + steps);

                                    working = document.getData().get("today_pedometer").toString();


                                    textView2.setText(" " + working);
                                }
                            }
                        }
                    });
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }

        }

    }

    //////////////////////////////////////////
    private void show_setting_popup() {
        setting_select_dialog.show();

        Button profile_btn = setting_select_dialog.findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_select_dialog.dismiss();
                Intent intent = new Intent(C_MainActivity.this, D_Profile.class);
                startActivity(intent);
//                finish();
            }
        });


        quit_dialog = new Dialog(C_MainActivity.this);

        quit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quit_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quit_dialog.setCancelable(false);
        quit_dialog.setContentView(R.layout.quit_dialog);

        Button quit_btn = (Button) setting_select_dialog.findViewById(R.id.quit_btn);
        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_select_dialog.dismiss();
                quitpopup();

            }
        });
        logout_dialog = new Dialog(C_MainActivity.this);

        logout_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logout_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logout_dialog.setCancelable(false);
        logout_dialog.setContentView(R.layout.logout_dialog);

        Button logout_btn = (Button) setting_select_dialog.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToast("로그아웃 되었습니다.");
                finish();
                Intent intent = new Intent(C_MainActivity.this, B_LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void quitpopup() {
        quit_dialog.show();

        Button nobtn = quit_dialog.findViewById(R.id.nobtn);
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit_dialog.dismiss();
            }
        });

        Button yesbtn = quit_dialog.findViewById(R.id.yesbtn);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startToast("계정이 탈퇴되었습니다.");
                        finish();
                        Intent intent = new Intent(C_MainActivity.this, B_LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void logoutpopup() {
        logout_dialog.show();

        Button nobtn = logout_dialog.findViewById(R.id.nobtn);
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_dialog.dismiss();
            }
        });

        Button yesbtn = logout_dialog.findViewById(R.id.yesbtn);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_dialog.dismiss();

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(C_MainActivity.this, B_LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showpopup1() {
        quizDialog1.show();

        Button obtn = quizDialog1.findViewById(R.id.o);
        obtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizDialog1.dismiss();
                Toast.makeText(C_MainActivity.this, "땡! 영수증은 일반쓰레기예요.", Toast.LENGTH_SHORT).show();
            }
        });

        Button xbtn = quizDialog1.findViewById(R.id.x);
        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizDialog1.dismiss();
                showpopup2();
                Toast.makeText(C_MainActivity.this, "딩동댕! 두 문제 남았어요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showpopup2() {
        quizDialog2.show();

        Button obtn = quizDialog2.findViewById(R.id.o);
        obtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizDialog2.dismiss();
                showpopup3();
                Toast.makeText(C_MainActivity.this, "딩동댕! 한 문제 남았어요.", Toast.LENGTH_SHORT).show();
            }
        });

        Button xbtn = quizDialog2.findViewById(R.id.x);
        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizDialog2.dismiss();
                Toast.makeText(C_MainActivity.this, "땡! 아이스팩은 일반쓰레기예요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showpopup3() {
        quizDialog3.show();

        Button obtn = quizDialog3.findViewById(R.id.o);
        obtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("User")
                        .whereEqualTo("userID", user.getUid()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        int coin = Integer.parseInt(document.getData().get("coin").toString());
                                        coin = coin + 5;

                                        userRef = db.collection("User").document(user.getUid());
                                        userRef.update("coin", coin);
                                        G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                        mpxp.add_XP(user.getUid(), 5);


                                        db.collection("QuizState")
                                                .whereEqualTo("userID", user.getUid()).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                document.getReference().delete();
                                                            }

                                                            QuizState update_quizstate = new QuizState(user.getUid(), true);
                                                            db.collection("QuizState").add(update_quizstate);

                                                        }
                                                    }
                                                });


                                    }

                                    try {
                                        Thread.sleep(1000);
                                        quizDialog3.dismiss();

                                        Intent intent = new Intent(C_MainActivity.this, C_MainActivity.class);
                                        startActivity(intent);

                                        Toast.makeText(C_MainActivity.this, "딩동댕! 경험치와 포인트가 적립됐어요.", Toast.LENGTH_SHORT).show();

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        });

            }
        });

        Button xbtn = quizDialog3.findViewById(R.id.x);
        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizDialog3.dismiss();
                Toast.makeText(C_MainActivity.this, "땡! 칫솔은 일반쓰레기예요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void userCoin() {
        if (user != null) { //로그인된 사용자 있음

            //User 객체 업데이트
            DocumentReference docRef = db.collection("User").document(user.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userInfo = documentSnapshot.toObject(User.class);
                    point = userInfo.getCoin();
                    String txt = String.valueOf(point);
                    //startToast(String.valueOf(point));
                    TextView textView2 = (TextView) findViewById(R.id.main_coin);
                    textView2.setText(txt);
                }
            });
        } else { //로그인된 사용자 없음
            startToast("No user is signed in");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //메인프로필사진 세팅
    private void Setting_profile(String profileurl) {
        profile = (ImageView) findViewById(R.id.main_profile);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("Empty_Profile.png");
        if (profileurl != null) { //프로파일이미지가 있다면
            pathReference = storageReference.child(user.getUid() + "Profile.jpg");

        }
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(C_MainActivity.this).load(uri).circleCrop().into(profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}

