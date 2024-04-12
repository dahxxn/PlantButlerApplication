package com.example.myapplication2;

import static android.widget.Toast.makeText;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class B_LoginActivity extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;
    EditText input_email, input_pw;
    Button contin;
    Dialog findpwDial;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String email, user_password;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        contin = (Button) findViewById(R.id.contin);

        //로그인버튼
        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_email = (EditText) findViewById(R.id.input_id);
                input_pw = (EditText) findViewById(R.id.input_pw);

                String email = input_email.getText().toString();
                String pw = input_pw.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(B_LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(B_LoginActivity.this, C_MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(B_LoginActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(B_LoginActivity.this, "이메일/비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        findpwDial = new Dialog(B_LoginActivity.this);
        findpwDial.requestWindowFeature(Window.FEATURE_NO_TITLE);
        findpwDial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        findpwDial.setCancelable(false);
        findpwDial.setContentView(R.layout.findpw);

        //회원 가입 버튼: 클릭시 회원가입 화면으로 전환
        Button register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), C_SignUpActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 버튼
        Button find_pw_btn = (Button) findViewById(R.id.find_pw_btn);
        find_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup1();
            }
        });
    }

    private void showpopup1() {
        findpwDial.show();

        EditText etEmail = (EditText) findpwDial.findViewById(R.id.etEmail);

        Button obtn = findpwDial.findViewById(R.id.registerBtn);
        obtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etEmail.getText().toString();

                db.collection("User")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        try {
                                            //password불러오기
                                            user_password = document.getData().get("password").toString();
                                            TextView password = (TextView) findpwDial.findViewById(R.id.password);
                                            password.setText("비밀번호: " + user_password);
                                            count++;
                                        } catch (Exception e) {
                                            startToast("존재하지 않는 이메일입니다.");
                                        }
                                    }
                                }
                            }
                        });
                if (count == 0)
                    startToast("존재하지 않는 이메일입니다.");
            }

        });
        Button xbtn = findpwDial.findViewById(R.id.x);
        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findpwDial.dismiss();
            }
        });
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}