package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class C_SignUpActivity extends AppCompatActivity {
    private static final String TAG = "C_SignUpActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_signup);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.register_next_btn).setOnClickListener(onClickListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }


    View.OnClickListener onClickListener = (v) -> {
        switch (v.getId()) {
            case R.id.register_next_btn:
                signUp();
                break;
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_passwordCheck_btn:
                checkPassword();
                break;
        }
    }

    private void checkPassword() {
        String password = ((EditText) findViewById(R.id.register_password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.register_passwordCheck)).getText().toString();
        if (password.length() > 0 && passwordCheck.length() > 0) {
            if (password.equals(passwordCheck)) {
                startToast("비밀번호가 일치합니다.");
            } else {
                startToast("비밀번호가 일치하지 않습니다.");
            }
        } else {
            startToast("비밀번호를 입력해 주세요");
        }
    }

    private void signUp() {
        String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.register_password)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.register_passwordCheck)).getText().toString();
        String nickname = ((EditText) findViewById(R.id.register_nickname)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0 && nickname.length() > 0) { //정보 전부 입력됐는지 확인
            if (password.equals(passwordCheck)) { //비밀번호 일치함
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, (task) -> {
                            if (task.isSuccessful()) { //Authentication에 등록 성공
                                FirebaseUser user = mAuth.getCurrentUser();

                                //firestore에 User database 저장---
                                User userInfo = new User(user.getUid(),email, password, nickname, 1, 0, "", 0, null);
                                db.collection("User").document(user.getUid())
                                        .set(userInfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startToast("회원가입에 성공하였습니다!");
                                                Intent intent = new Intent(C_SignUpActivity.this, B_LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                startToast("erro: add User document");
                                            }
                                        });

                                //firestore에 garden 생성 : gardenID = userID_garden으로 설정
                                String uid = user.getUid();
                                String garden_id = uid.concat("_garden");
                                Garden garden = new Garden(garden_id,uid,"","","","","");
                                db.collection("Garden").add(garden);

                                //firebase에 ranking에 사용자 정보 추가
                                Ranking ranking = new Ranking(garden_id,0,uid,0);
//                                db.collection("Ranking").add(ranking);
                                db.collection("Ranking").document(user.getUid())
                                        .set(ranking)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) { }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) { }
                                        });

                                //firebase에 userPedometer에 사용자 정보 추가
                                UserPedometer pedo = new UserPedometer(user.getUid(),  0,0,0);
                                db.collection("UserPedometer").document(user.getUid())
                                        .set(pedo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) { }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) { }
                                        });

                                //firebase에 quizstate에 사용자 정보 추가
                                QuizState quizstate = new QuizState(uid, false);
                                db.collection("QuizState").add(quizstate);

                                //firebase에 userItem set
                                UserItem userItem = new UserItem(uid);
                                db.collection("UserItem").document(uid)
                                        .set(userItem);

                                //firebase에 missionState에 사용자 정보 추가
                                MissionState missionstate = new MissionState(uid);
                                db.collection("MissionState").document(uid).set(missionstate);


                            }
                            else { ////Authentication에 등록 실패
                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }
                        });
            }
            else { //비밀번호 일치 안함
                startToast("비밀번호가 일치하지 않습니다.");
            }
        }
        else { //정보 입력 전부 안됨
            startToast("다시 입력해 주세요");
        }
    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}