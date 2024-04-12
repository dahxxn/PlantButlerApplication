//package com.example.myapplication2;
//
//import static android.widget.Toast.makeText;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.util.ArrayList;
//
//public class G_Social_board_enter_writing extends AppCompatActivity {
//
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private FirebaseAuth mAuth;
//    private FirebaseUser user ;
//
//    String title, content, profileurl, nickname, userID;
//    String like;
//
//    ImageView profile_img;
//    ImageButton heart;
//    ArrayList<String> writer= new ArrayList<String>();
//    int like_amt=0;
//    TextView txt3;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gsocial_board_enter_writing);
//
//        //인텐트값 가져오기
//        Intent writing_intent  = getIntent();
//        String what_btn = writing_intent.getStringExtra("throw");
//
//
//        //게시판 정보 가져오기
//        mAuth = FirebaseAuth.getInstance();
//        user = mAuth.getCurrentUser();
//        db.collection("SocialBoard")
//                .whereEqualTo("boardID", what_btn)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot document : task.getResult()){
//                                try{
//                                    //title 바꾸기
//                                    title = document.getData().get("boardTitle").toString();
//                                    TextView txt = (TextView) findViewById(R.id.writing_title) ;
//                                    txt.setText(title) ;
//
//                                    //content 바꾸기
//                                    content = document.getData().get("boardContent").toString();
//                                    TextView txt2 = (TextView) findViewById(R.id.writing_content) ;
//                                    txt2.setText(content) ;
//
//                                    //Like 바꾸기
//                                    like = document.getData().get("boardLike").toString();
//                                    like_amt=Integer.parseInt(like);
//                                    txt3 = (TextView) findViewById(R.id.heart_amt) ;
//                                    txt3.setText(like) ;
//
//                                    userID=document.getData().get("userID").toString();
//                                    Setting_Profile(userID);
//
//
//
//                                }catch (Exception e){
//                                    makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    }
//                });
//
//        //프로필변경
//
//        //이전 버튼
//        Button back = (Button) findViewById(R.id.board_back);
//        back.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext() , F_SocialB.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    public void Setting_Profile(String userID){
//        db.collection("User")
//                .whereEqualTo("userID",userID)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(QueryDocumentSnapshot document : task.getResult()){
//                                try{
//
//                                    //닉네임 불러오기
//                                    nickname = document.getData().get("nickName").toString();
//                                    TextView textView2 = (TextView) findViewById(R.id.name) ;
//                                    textView2.setText(nickname) ;
//                                    //프로파일사진 url불러오기
//                                    try {
//                                        profile_img = (ImageView) findViewById(R.id.profile);
//                                        profileurl = document.getData().get("profileImage").toString();
//                                        FirebaseStorage storage = FirebaseStorage.getInstance();
//                                        StorageReference storageReference = storage.getReference();
//                                        StorageReference pathReference = storageReference.child("Empty_Profile.jpg");
//                                        if (profileurl != null) { //프로파일이미지가 있다면
//                                            pathReference = storageReference.child(user.getUid()+"Profile.jpg");
//
//                                        }
//                                        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
//                                                Glide.with(G_Social_board_enter_writing.this).load(uri).circleCrop().into(profile_img);
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//
//                                            }
//                                        });
//                                    } catch (Exception exception){
//
//                                    }
//
//
//                                }catch (Exception e){
//
//                                }
//                            }
//                        }
//                    }
//                });
//    }
//}

package com.example.myapplication2;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class G_Social_board_enter_writing extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user ;

    String title, content, profileurl, nickname, userID, now,what_btn;
    String like;

    ImageView profile_img;
    ImageButton heart;
    ArrayList<String> writer= new ArrayList<String>();
    static int like_amt=0;
    TextView txt3, heart_amt;
    ImageButton heart_btn;
    int like_amt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsocial_board_enter_writing);

        //인텐트값 가져오기
        Intent writing_intent  = getIntent();
        what_btn = writing_intent.getStringExtra("throw");

        heart_btn=findViewById(R.id.heart_btn);
        heart_amt=findViewById(R.id.heart_amt);

        //게시판 정보 가져오기
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db.collection("SocialBoard")
                .whereEqualTo("boardID", what_btn)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                try{
                                    //title 바꾸기
                                    title = document.getData().get("boardTitle").toString();
                                    TextView txt = (TextView) findViewById(R.id.writing_title) ;
                                    txt.setText(title) ;

                                    //content 바꾸기
                                    content = document.getData().get("boardContent").toString();
                                    TextView txt2 = (TextView) findViewById(R.id.writing_content) ;
                                    txt2.setText(content) ;

                                    now = document.getData().get("boardDate").toString();


                                    //Like 바꾸기
                                    like = document.getData().get("boardLike").toString();
                                    like_amt1 = Integer.parseInt(like);

                                    txt3 = (TextView) findViewById(R.id.heart_amt) ;
                                    txt3.setText(like) ;

                                    userID=document.getData().get("userID").toString();

                                    Setting_Profile(userID);

                                    heart_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if ((document.getData().get("userID").toString()) == (user.getUid().toString())) {
                                                //만약 현재 로그인한 사람의 아이디라면?

                                            } else {
                                                DocumentReference washingtonRef = db.collection("SocialBoard").document(userID);
                                                makeText(getApplicationContext(), "좋아요 1증가", Toast.LENGTH_SHORT).show();

                                                washingtonRef
                                                        .update("boardLike", like_amt1 + 1)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                //Like 바꾸기
                                                                like_amt1 += 1;
                                                                heart_amt.setText(String.valueOf(like_amt1));


                                                                Update_Ranking(userID,like_amt1);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        });
                                            }
                                        }
                                    });


                                }catch (Exception e){
                                    makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

        //프로필변경

        //이전 버튼
        Button back = (Button) findViewById(R.id.board_back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , F_SocialB.class);
                startActivity(intent);
            }
        });

    }

    public void Update_Ranking(String userID, int like_amt1){

        db.collection("Ranking")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                try{
                                    DocumentReference washingtonRef = db.collection("Ranking").document(userID);

                                    washingtonRef
                                            .update("likeCount", like_amt1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    //Like 바꾸기

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });

                                }catch (Exception e){
                                }
                            }
                        }
                    }
                });
    }
    public void Setting_Profile(String userID){

        db.collection("User")
                .whereEqualTo("userID",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                try{
                                    //닉네임 불러오기
                                    nickname = document.getData().get("nickName").toString();
                                    TextView textView2 = (TextView) findViewById(R.id.name) ;
                                    textView2.setText(nickname) ;
                                    profile_img = (ImageView) findViewById(R.id.profile);

                                    //프로파일사진 url불러오기
                                    try {
                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        StorageReference storageReference = storage.getReference();
                                        StorageReference pathReference;

                                        if (document.getData().get("profileImage") != null) {
                                            profileurl = document.getData().get("profileImage").toString();
                                            pathReference = storageReference.child(userID+"Profile.jpg");
                                            pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(G_Social_board_enter_writing.this).load(uri).circleCrop().into(profile_img);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });

                                        } else {
                                            profile_img.setImageResource(R.drawable.girl);
                                        }

                                    } catch (Exception exception){

                                    }


                                }catch (Exception e){

                                }
                            }
                        }
                    }
                });
    }


}