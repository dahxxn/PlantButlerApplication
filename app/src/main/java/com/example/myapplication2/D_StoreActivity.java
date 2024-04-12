package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class D_StoreActivity extends AppCompatActivity {
    private Button back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_store);

        //뒤로가기
        back_btn=findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(D_StoreActivity.this, C_MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton dirt_btn = (ImageButton) findViewById(R.id.store_dirt_btn);
        dirt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(D_StoreActivity.this, E_Store_dirtActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton nut_btn = (ImageButton) findViewById(R.id.store_nut_btn);
        nut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(D_StoreActivity.this, E_Store_nutrientActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton pot_btn = (ImageButton) findViewById(R.id.store_pot_btn);
        pot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(D_StoreActivity.this, E_Store_potActivity.class);
                startActivity(intent);
                finish();

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
}