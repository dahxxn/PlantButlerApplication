package com.example.myapplication2;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class D_Profile extends AppCompatActivity {
    String email, user_password;
    String nickname, ID, profileurl;
    Button down, back;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    Button complete_setting;
    ImageView profile, profile_btn, profile2, change_email, change_nickname, pw_btn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user ;

    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_profile);



        //이메일 초기 세팅
        FirebaseUser first_email = FirebaseAuth.getInstance().getCurrentUser();

        email = first_email.getEmail();

        Uri photoUrl = first_email.getPhotoUrl();
        boolean emailVerified = first_email.isEmailVerified();
        String uid = first_email.getUid();

        TextView textView1 = (TextView) findViewById(R.id.email_text) ;
        textView1.setText(email) ;


        //세팅완료
        complete_setting = findViewById(R.id.complete_setting);
        complete_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(D_Profile.this, C_MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(D_Profile.this, C_MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        //프로파일 이미지 수정
        profile_btn = findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D_Profile.this, E_Profile_Image_Edit.class);
                startActivity(intent);
                finish();

            }
        });
        change_email = findViewById(R.id.email_btn);
        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D_Profile.this, E_Change_email.class);
                startActivity(intent);
                finish();

            }
        });

        //닉네임 변경 버튼클릭
        change_nickname = findViewById(R.id.nickname_btn);
        change_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D_Profile.this, E_Change_nickname.class);
                startActivity(intent);
                finish();
            }
        });

        //비밀번호 변경 버튼클릭
        pw_btn = findViewById(R.id.password_btn);
        pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D_Profile.this, E_Change_password.class);
                startActivity(intent);
                finish();
            }
        });


        //초기 세팅
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

                                    //닉네임 불러오기
                                    nickname = document.getData().get("nickName").toString();

                                    TextView textView2 = (TextView) findViewById(R.id.nickname_text) ;
                                    textView2.setText(nickname) ;
                                    //프로파일사진 url불러오기
                                    try {
                                        profileurl = document.getData().get("profileImage").toString();
                                        Setting_profile(profileurl);
                                    } catch (Exception exception){

                                    }

                                    //이메일 불러오기
                                    email = document.getData().get("email").toString();

                                    //password불러오기
                                    user_password = document.getData().get("password").toString();
                                    TextView textView3 = (TextView) findViewById(R.id.password) ;
                                    textView3.setText(user_password) ;

                                }catch (Exception e){
                                    makeText(getApplicationContext(), profileurl, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });




    }//oncreate


    //프로파일 세팅
    private void Setting_profile(String profileurl){
        profile_btn = findViewById(R.id.profile_btn);
        profile2 = (ImageView) findViewById(R.id.profile);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("Empty_Profile.jpg");
        if (profileurl != null) { //프로파일이미지가 있다면
            pathReference = storageReference.child(user.getUid()+"Profile.jpg");

        }
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(D_Profile.this).load(uri).circleCrop().into(profile2);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    //닉네임 변경버튼
    private void test(){
        DocumentReference docRef = db.collection("User").document(""+ID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nickname = "" + document.getData().get("userName");
                        TextView textView2 = (TextView) findViewById(R.id.nickname_text) ;
                        textView2.setText(nickname) ;
                    } else {
                        makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    makeText(getApplicationContext(), "get failed with ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}