package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class D_SellectActivity extends AppCompatActivity {
    LinearLayout linear1, linear2;
    Dialog dialog1, dialog2, namedialog;
    EditText etPlantName;

    String plantType="";

    private static final String TAG = "D_SellectActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_sellect);

        mAuth = FirebaseAuth.getInstance();


        dialog1 = new Dialog(D_SellectActivity.this);
        dialog2 = new Dialog(D_SellectActivity.this);
        namedialog = new Dialog(D_SellectActivity.this);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);

        ///////////////////////////글씨////////////////////////////////
        TextView title = (TextView) findViewById(R.id.title1);
        String content = title.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "원하는 식물";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#03A63C")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new TypefaceSpan("nanumsquare_exbold"), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(spannableString);

        /////////////////////////////////////////////////////////////////////

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.select_plant1_dialog);

        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.select_plant2_dialog);

        namedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        namedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        namedialog.setCancelable(false);
        namedialog.setContentView(R.layout.select_name_dialog);

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup1();
            }
        });

        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup2();
            }
        });

    }

    public void showpopup1() {
        dialog1.show();
        Button nobtn = dialog1.findViewById(R.id.nobtn);
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        Button yesbtn = dialog1.findViewById(R.id.yesbtn);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                showpopup3();
                plantType="몬스테라";

            }
        });
    }

    public void showpopup2() {
        dialog2.show();
        Button nobtn = dialog2.findViewById(R.id.nobtn);
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        Button yesbtn = dialog2.findViewById(R.id.yesbtn);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
                showpopup3();
                plantType="해바라기";
            }
        });
    }

    public void showpopup3() {
        namedialog.show();

        Button yesbtn = namedialog.findViewById(R.id.yesbtn);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etPlantName = (EditText) namedialog.findViewById(R.id.etPlantName);
                String data = etPlantName.getText().toString();
                namedialog.dismiss();// 토스트
                Toast.makeText(D_SellectActivity.this, data, Toast.LENGTH_SHORT).show();

                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                String plant_id = uid.concat(data);
                UserPlant plantInfo = new UserPlant(plant_id, uid, plantType, data, "pot_1");
                db.collection("UserPlant").add(plantInfo);

                DocumentReference userRef = db.collection("User").document(user.getUid());
                userRef.update("currentPlant",data);

                Intent intent = new Intent(D_SellectActivity.this, C_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}