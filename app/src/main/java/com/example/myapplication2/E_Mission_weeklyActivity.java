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

public class E_Mission_weeklyActivity extends AppCompatActivity {
    TextView week_30000,week_50000,week_70000;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_weekly);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //        이전 버튼 추가
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), D_MissionActivity.class);
                startActivity(intent);
            }
        });

        week_30000 = (TextView) findViewById(R.id.week_30000_check);
        week_30000.setOnClickListener(new View.OnClickListener() {
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
                                        m_state = Boolean.parseBoolean(dcm.getData().get("week_30000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_weeklyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
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
                                                                    String current_week_pedo = document.getData().get("week_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_week_pedo) >= 30000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        week_30000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("week_30000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_weeklyActivity.this, E_Mission_weeklyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_weeklyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_weeklyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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

        week_50000 = (TextView) findViewById(R.id.week_50000_check);
        week_50000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xp_value = 15;

                db.collection("MissionState").whereEqualTo("userID", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    boolean m_state = false;

                                    for(QueryDocumentSnapshot dcm : task.getResult()){
                                        m_state = Boolean.parseBoolean(dcm.getData().get("week_50000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_weeklyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
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
                                                                    String current_week_pedo = document.getData().get("week_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_week_pedo) >= 50000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        week_50000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("week_50000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_weeklyActivity.this, E_Mission_weeklyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_weeklyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_weeklyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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

        week_70000 = (TextView) findViewById(R.id.week_70000_check);
        week_70000.setOnClickListener(new View.OnClickListener() {
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
                                        m_state = Boolean.parseBoolean(dcm.getData().get("week_70000").toString());

                                        if(m_state){
                                            Toast.makeText(E_Mission_weeklyActivity.this, "완료된 미션입니다!", Toast.LENGTH_SHORT).show();
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
                                                                    String current_week_pedo = document.getData().get("week_pedometer").toString();
                                                                    G_ManagePlantXP mpxp = new G_ManagePlantXP();
                                                                    G_ManageCoin mc = new G_ManageCoin();

                                                                    if(Integer.parseInt(current_week_pedo) >= 70000){
                                                                        mpxp.add_XP(user.getUid(), xp_value);
                                                                        mc.add_Coin(user.getUid(),xp_value);
                                                                        week_70000.setClickable(false);

                                                                        DocumentReference missionRef = db.collection("MissionState").document(user.getUid());
                                                                        missionRef.update("week_70000",true);

                                                                        try {
                                                                            Thread.sleep(1000);
                                                                            Intent intent = new Intent(E_Mission_weeklyActivity.this, E_Mission_weeklyActivity.class);
                                                                            startActivity(intent);

                                                                        } catch (InterruptedException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        Toast.makeText(E_Mission_weeklyActivity.this,"미션 성공!",Toast.LENGTH_SHORT).show();

                                                                    }else{
                                                                        Toast.makeText(E_Mission_weeklyActivity.this,"미션실패",Toast.LENGTH_SHORT).show();
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
