package com.example.myapplication2;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class E_Change_email extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    String nickname, email, password, profileurl;
    Button change,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echange_email);


//사용자정보 먼저 가져오기
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        db.collection("User")
                .whereEqualTo("userID",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                try{
                                    //이메일 불러오기
                                    email = document.getData().get("email").toString();
                                    TextView textView2 = (TextView) findViewById(R.id.origin_email) ;
                                    textView2.setText("origin email : " +email) ;

                                }catch (Exception e){
                                    makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

        back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(E_Change_email.this, C_MainActivity.class);
                startActivity(intent);
            }
        });
        //확인버튼클릭
        change = (Button) findViewById(R.id.email_change_btn);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Change_Email();
            }
        });

    }//oncreate

    private void Change_Email(){
        //이메일만 변경하기
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (task) -> {
                    if (task.isSuccessful()) { //Authentication에 등록 성공
                        FirebaseUser user = mAuth.getCurrentUser();

                        //firestore에 User database 저장---
                        User userInfo = new User(user.getUid(), email, password, nickname, 0, 0, null, 0, profileurl);
                        db.collection("User")
                                .add(userInfo)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Intent intent = new Intent(E_Change_email.this, D_Profile.class);
                                        startActivity(intent);
                                        makeText(getApplicationContext(), "이메일 변경 성공", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        makeText(getApplicationContext(), "이메일 변경 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
    }
}