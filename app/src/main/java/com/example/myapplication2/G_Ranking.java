package com.example.myapplication2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class G_Ranking extends AppCompatActivity {
    Button back_btn;
    String email, profileurl, name1, name2, name3, name4, name5, name6;

    ImageView profile1, profile2, profile3;
    TextView profile1_name, profile2_name, profile3_name, profile4_name, profile5_name, profile6_name, like1, like2, like3, like4, like5, like6;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    ArrayList<String> rank_id = new ArrayList<>();
    ArrayList<String> rank_score = new ArrayList<>();
    String id, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_ranking);

        profile1 = (ImageView) findViewById(R.id.person1);
        profile2 = (ImageView) findViewById(R.id.person2);
        profile3 = (ImageView) findViewById(R.id.person3);

        profile1_name = (TextView) findViewById(R.id.person1_name);
        profile2_name = (TextView) findViewById(R.id.person2_name);
        profile3_name = (TextView) findViewById(R.id.person3_name);
        profile4_name = (TextView) findViewById(R.id.person4_name);
        profile5_name = (TextView) findViewById(R.id.person5_name);
        profile6_name = (TextView) findViewById(R.id.person6_name);

        like1 = (TextView) findViewById(R.id.like_amt1);
        like2 = (TextView) findViewById(R.id.like_amt2);
        like3 = (TextView) findViewById(R.id.like_amt3);
        like4 = (TextView) findViewById(R.id.like_amt4);
        like5 = (TextView) findViewById(R.id.like_amt5);
        like6 = (TextView) findViewById(R.id.like_amt6);

        back_btn = findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(G_Ranking.this, D_CommunityMainActivity.class);
                startActivity(intent);

            }
        });
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //랭킹에서 like개수 모두 가져와서 정렬 후, 6명의 id 가져오기
        db.collection("Ranking")
                .orderBy("likeCount", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            int i = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (i < 7) {
                                    id = document.getData().get("userID").toString();
                                    rank_id.add(id);
                                    score = document.getData().get("likeCount").toString();
                                    rank_score.add(score);
                                    Setting_rank(i, id);

                                    switch (i) {
                                        case 1:
                                            like1.setText(score);
                                            break;
                                        case 2:
                                            like2.setText(score);
                                            break;
                                        case 3:
                                            like3.setText(score);
                                            break;
                                        case 4:
                                            like4.setText(score);
                                            break;
                                        case 5:
                                            like5.setText(score);
                                            break;
                                        case 6:
                                            like6.setText(score);
                                            break;
                                    }
                                    i++;
                                } else break;
                            }
                        }
                    }
                });

    }

    private void Setting_rank(int i, String id) {
        db.collection("User")
                .whereEqualTo("userID", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    switch (i) {
                                        case 1:
                                            name1 = document.getData().get("nickName").toString();
                                            profile1_name.setText(name1);
                                            profile1_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), F_RankingGardenActivity.class);
                                                    intent.putExtra("uid", id);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case 2:
                                            name2 = document.getData().get("nickName").toString();
                                            profile2_name.setText(name2);
                                            profile2_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), F_RankingGardenActivity.class);
                                                    intent.putExtra("uid", id);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case 3:
                                            name3 = document.getData().get("nickName").toString();
                                            profile3_name.setText(name3);
                                            profile3_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), F_RankingGardenActivity.class);
                                                    intent.putExtra("uid", id);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case 4:
                                            name4 = document.getData().get("nickName").toString();
                                            profile4_name.setText(name4);
                                            profile4_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), F_RankingGardenActivity.class);
                                                    intent.putExtra("uid", id);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case 5:
                                            name5 = document.getData().get("nickName").toString();
                                            profile5_name.setText(name5);
                                            profile5_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), F_RankingGardenActivity.class);
                                                    intent.putExtra("uid", id);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                        case 6:
                                            name6 = document.getData().get("nickName").toString();
                                            profile6_name.setText(name6);
                                            profile6_name.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), F_RankingGardenActivity.class);
                                                    intent.putExtra("uid", id);
                                                    startActivity(intent);
                                                }
                                            });
                                            break;
                                    }


                                    if (document.getData().get("profileImage") != null) {
                                        profileurl = document.getData().get("profileImage").toString();
                                        Setting_profile(profileurl, i, id);
                                    } else {
                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        StorageReference storageReference = storage.getReference();
                                        StorageReference pathReference;

                                        pathReference = storageReference.child("Empty_Profile.png");
                                        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                switch (i) {
                                                    case 1:
                                                        Glide.with(G_Ranking.this).load(uri).into(profile1);
                                                        break;
                                                    case 2:
                                                        Glide.with(G_Ranking.this).load(uri).into(profile2);
                                                        break;
                                                    case 3:
                                                        Glide.with(G_Ranking.this).load(uri).into(profile3);
                                                        break;
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                                    }

                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                });
    }

    private void Setting_profile(String profileurl, int i, String id) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("Empty_Profile.png");
        ;

        if (profileurl != null) {
            pathReference = storageReference.child(id + "Profile.jpg");
            //pathReference = storageReference.child("Profile.jpg");
        }

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                switch (i) {
                    case 1:
                        Glide.with(G_Ranking.this).load(uri).circleCrop().into(profile1);
                        break;
                    case 2:
                        Glide.with(G_Ranking.this).load(uri).circleCrop().into(profile2);
                        break;
                    case 3:
                        Glide.with(G_Ranking.this).load(uri).circleCrop().into(profile3);
                        break;

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}