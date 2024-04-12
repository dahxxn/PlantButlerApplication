package com.example.myapplication2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class D_CommunityMainActivity extends AppCompatActivity {
    Button news_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        news_btn=findViewById(R.id.c_news_btn);

        news_btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(D_CommunityMainActivity.this, F_News.class);
                startActivity(intent);

            }
        });

        // 이전 버튼 추가
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), C_MainActivity.class);
                startActivity(intent);
            }
        });

        Button mygardenBtn = (Button) findViewById(R.id.mygardenBtn);
        mygardenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), E_MygardenActivity.class);
                startActivity(intent);
            }
        });

        Button notice_btn = (Button)findViewById(R.id.notice_board_btn);
        notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), F_SocialB.class);
                startActivity(intent);
            }
        });

        Button rank_btn = (Button)findViewById(R.id.c_rank_btn);
        rank_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), G_Ranking.class);
                startActivity(intent);
            }
        });

    }
}