package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class E_Mission_dailyActivity extends AppCompatActivity {
    TextView daily_3000, daily_4000, daily_5000, daily_10000, daily_15000, daily_30000, daily_50000;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_daily);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // 이전 버튼 추가
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), D_MissionActivity.class);
                startActivity(intent);
            }
        });

        daily_3000 = (TextView) findViewById(R.id.daily_3000_check);
        daily_3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 1;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_3000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 3000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_3000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_3000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }


                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

        daily_4000 = (TextView) findViewById(R.id.daily_4000_check);
        daily_4000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 2;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_4000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 4000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_4000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_4000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

        daily_5000 = (TextView) findViewById(R.id.daily_5000_check);
        daily_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 3;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_5000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 5000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_5000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_5000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

        daily_10000 = (TextView) findViewById(R.id.daily_10000_check);
        daily_10000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 10;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_10000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 10000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_10000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_10000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

        daily_15000 = (TextView) findViewById(R.id.daily_15000_check);
        daily_15000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 20;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_15000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 15000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_15000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_15000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

        daily_30000 = (TextView) findViewById(R.id.daily_30000_check);
        daily_30000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 30;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_30000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 30000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_30000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_30000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

        daily_50000 = (TextView) findViewById(R.id.daily_50000_check);
        daily_50000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 50;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("daily_50000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_dailyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            db.collection("UserPedometer")
                                                    .whereEqualTo("userID", user.getUid())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
//                                    Toast.makeText(E_Mission_dailyActivity.this,"만보기 데베 접근!",Toast.LENGTH_SHORT).show();

                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    String current_daily_pedo = document.getData().get("today_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_daily_pedo) >= 50000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        daily_50000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("daily_50000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_dailyActivity.this, E_Mission_dailyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_dailyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
            }
        });

    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , D_MissionActivity.class);
        startActivity(intent);
        finish();
    }


}
