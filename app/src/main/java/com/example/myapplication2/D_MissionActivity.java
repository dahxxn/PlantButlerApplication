package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class D_MissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_mission);

        Button daily_button = (Button) findViewById(R.id.dailyBtn);
        daily_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_dailyActivity.class);
                startActivity(intent);
            }
        });

        Button weekly_button = (Button) findViewById(R.id.weeklyBtn);
        weekly_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_weeklyActivity.class);
                startActivity(intent);
            }
        });

        Button select_button = (Button) findViewById(R.id.selectBtn);
        select_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_selectActivity.class);
                startActivity(intent);
            }
        });

        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_confirmActivity.class);
                startActivity(intent);
            }
        });

//        이전 버튼 추가
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), C_MainActivity.class);
                startActivity(intent);
            }
        });




    }
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , C_MainActivity.class);
        startActivity(intent);
        finish();
    }
}