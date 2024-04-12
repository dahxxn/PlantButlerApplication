package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class E_Mission_selectActivity extends AppCompatActivity {
    ImageView trash_btn;
    ImageView etc_btn;
    ImageView transportation_btn;
    ImageView basket_btn;
    ImageView tumbler_btn;
    ImageView separate_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_select);

        etc_btn = (ImageView) findViewById(R.id.etc);
        trash_btn = (ImageView) findViewById(R.id.trash);
        basket_btn = (ImageView) findViewById(R.id.basket);
        tumbler_btn = (ImageView) findViewById(R.id.tumbler);
        separate_btn = (ImageView) findViewById(R.id.separate);
        transportation_btn = (ImageView) findViewById(R.id.public_transportation_button);

        trash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_trashPhoto_upload.class);
                startActivity(intent);
            }
        });

        etc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_etcPhoto_upload.class);
                startActivity(intent);
            }
        });

        basket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_basketPhoto_upload.class);
                startActivity(intent);
            }
        });

        tumbler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_tumblerPhoto_upload.class);
                startActivity(intent);
            }
        });

        transportation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_transportationPhoto_upload.class);
                startActivity(intent);
            }
        });

        separate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , E_Mission_separatePhoto_upload.class);
                startActivity(intent);
            }
        });

        //        이전 버튼 추가

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), D_MissionActivity.class);
                startActivity(intent);
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
