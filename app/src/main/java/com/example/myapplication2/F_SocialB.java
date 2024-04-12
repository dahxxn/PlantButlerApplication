package com.example.myapplication2;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class F_SocialB extends AppCompatActivity {
    ImageButton go_pencil;
    Button go_back, btn1, btn2, btn3, btn4, btn5, btn6;
    Button heart_btn1,heart_btn2,heart_btn3,heart_btn4,heart_btn5,heart_btn6;
    String board_list, board_idlist, like;
    Integer heart1, heart2, heart3, heart4, heart5, heart6;
    TextView heart_txt1, heart_txt2;
    String id1, id2, id3, id4, id5, id6;
    int cnt;
    TextView textView, txt3;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user ;
    ArrayList<String> Title_list = new ArrayList<String>();
    ArrayList<String> Title_idlist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsocial_b);

        //게시판 목록 가져오기
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        cnt=0;
        db.collection("SocialBoard")
                .whereEqualTo("load", true)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                try{
                                    //불러온 제목 arraylist에 넣기
                                    board_list = "      "+document.getData().get("boardTitle").toString();
                                    Title_list.add(board_list);

                                    board_idlist = document.getData().get("boardID").toString();
                                    Title_idlist.add(board_idlist);

                                    textView = findViewById(R.id.board_w1);
                                    textView.setText(Title_list.get(0));

                                    like = document.getData().get("boardLike").toString();

                                    switch (cnt) {
                                        case 0:
                                            textView = findViewById(R.id.board_w1);
                                            textView.setText(Title_list.get(cnt));
                                            //Like 바꾸기
                                            txt3 = (TextView) findViewById(R.id.board_heart_1) ;
                                            txt3.setText(like) ;
                                            heart1 = Integer.parseInt(like);
                                            id1 = document.getData().get("userID").toString();
                                            break;
                                        case 1:
                                            textView = findViewById(R.id.board_w2);
                                            textView.setText(Title_list.get(cnt));
                                            txt3 = (TextView) findViewById(R.id.board_heart_2) ;
                                            txt3.setText(like) ;
                                            heart2 = Integer.parseInt(like);
                                            id2 = document.getData().get("userID").toString();
                                            break; // 종료
                                        case 2:
                                            textView = findViewById(R.id.board_w3);
                                            textView.setText(Title_list.get(cnt));
                                            txt3 = (TextView) findViewById(R.id.board_heart_3) ;
                                            txt3.setText(like) ;
                                            heart3 = Integer.parseInt(like);
                                            id3 = document.getData().get("userID").toString();
                                            break;
                                        case 3:
                                            textView = findViewById(R.id.board_w4);
                                            textView.setText(Title_list.get(cnt));
                                            txt3 = (TextView) findViewById(R.id.board_heart_4) ;
                                            txt3.setText(like) ;
                                            heart4 = Integer.parseInt(like);
                                            id4 = document.getData().get("userID").toString();
                                            break;
                                        case 4:
                                            textView = findViewById(R.id.board_w5);
                                            textView.setText(Title_list.get(cnt));
                                            txt3 = (TextView) findViewById(R.id.board_heart_5) ;
                                            txt3.setText(like) ;
                                            heart5 = Integer.parseInt(like);
                                            id5 = document.getData().get("userID").toString();
                                            break;
                                        case 5:
                                            textView = findViewById(R.id.board_w6);
                                            textView.setText(Title_list.get(cnt));
                                            txt3 = (TextView) findViewById(R.id.board_heart_6) ;
                                            txt3.setText(like) ;
                                            heart6 = Integer.parseInt(like);
                                            id6 = document.getData().get("userID").toString();
                                            break;
                                    }
                                    cnt+=1;




                                }catch (Exception e){
                                    makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });


        //이전버튼
        go_back = findViewById(R.id.g_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(F_SocialB.this, D_CommunityMainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        //1번 하트클릭
        db.collection("SocialBoard")
                .whereEqualTo("load", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       heart_btn1 = findViewById(R.id.board_heart_1);
                                                       heart_btn1.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View view) {


                                                               if ((document.getData().get("userID").toString()) == (user.getUid().toString())) {
                                                                   //만약 현재 로그인한 사람의 아이디라면?

                                                               } else {
                                                                   DocumentReference washingtonRef = db.collection("SocialBoard").document(id1);
                                                                   makeText(getApplicationContext(), "좋아요 1증가", Toast.LENGTH_SHORT).show();

                                                                   washingtonRef
                                                                           .update("boardLike", heart1 + 1)
                                                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                               @Override
                                                                               public void onSuccess(Void aVoid) {
                                                                                   //Like 바꾸기
                                                                                   heart_txt1 = (TextView) findViewById(R.id.board_heart_1);
                                                                                   heart1 += 1;
                                                                                   heart_txt1.setText(heart1.toString());
                                                                                   Update_Ranking(id1,heart1);
                                                                               }
                                                                           })
                                                                           .addOnFailureListener(new OnFailureListener() {
                                                                               @Override
                                                                               public void onFailure(@NonNull Exception e) {
                                                                                   //startToast("Error updating document");
                                                                               }
                                                                           });
                                                               }
                                                           }
                                                       });
                                                   }
                                               }
                                           }
                                       });




        //2번 하트클릭
        db.collection("SocialBoard")
                .whereEqualTo("load", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_btn2 = findViewById(R.id.board_heart_2);
                                heart_btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {



                                        if ((document.getData().get("userID").toString()) == (user.getUid().toString())) {
                                            //만약 현재 로그인한 사람의 아이디라면?

                                        } else {
                                            DocumentReference washingtonRef = db.collection("SocialBoard").document(id2);
                                            makeText(getApplicationContext(), "좋아요 1증가", Toast.LENGTH_SHORT).show();

                                            washingtonRef
                                                    .update("boardLike", heart2 + 1)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            //Like 바꾸기
                                                            heart_txt2 = (TextView) findViewById(R.id.board_heart_2);
                                                            heart2 += 1;
                                                            heart_txt2.setText(heart2.toString());
                                                            Update_Ranking(id2,heart2);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            //startToast("Error updating document");
                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

        //3번 하트클릭
        db.collection("SocialBoard")
                .whereEqualTo("load", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_btn3 = findViewById(R.id.board_heart_3);
                                heart_btn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {



                                        if ((document.getData().get("userID").toString()) == (user.getUid().toString())) {
                                            //만약 현재 로그인한 사람의 아이디라면?

                                        } else {
                                            DocumentReference washingtonRef = db.collection("SocialBoard").document(id3);
                                            makeText(getApplicationContext(), "좋아요 1증가", Toast.LENGTH_SHORT).show();

                                            washingtonRef
                                                    .update("boardLike", heart3 + 1)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            //Like 바꾸기
                                                            heart_txt2 = (TextView) findViewById(R.id.board_heart_3);
                                                            heart3 += 1;
                                                            heart_txt2.setText(heart3.toString());
                                                            Update_Ranking(id3,heart3 );
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            //startToast("Error updating document");
                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
        //4번 하트클릭
        db.collection("SocialBoard")
                .whereEqualTo("load", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_btn4 = findViewById(R.id.board_heart_4);
                                heart_btn4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DocumentReference washingtonRef = db.collection("SocialBoard").document(id4);

                                        washingtonRef
                                                .update("boardLike", heart4+1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        //Like 바꾸기
                                                        txt3 = (TextView) findViewById(R.id.board_heart_4) ;
                                                        heart4+=1;
                                                        txt3.setText(heart4.toString()) ;
                                                        Update_Ranking(id4,heart4 );
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        //startToast("Error updating document");
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    }
                });

        //5번 하트클릭
        db.collection("SocialBoard")
                .whereEqualTo("load", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_btn5 = findViewById(R.id.board_heart_5);
                                heart_btn5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DocumentReference washingtonRef = db.collection("SocialBoard").document(id5);

                                        makeText(getApplicationContext(), "좋아요 1증가", Toast.LENGTH_SHORT).show();

                                        washingtonRef
                                                .update("boardLike", heart5+1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        //Like 바꾸기
                                                        txt3 = (TextView) findViewById(R.id.board_heart_5) ;
                                                        heart5+=1;
                                                        txt3.setText(heart5.toString()) ;
                                                        Update_Ranking(id5,heart5);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        //startToast("Error updating document");
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    }
                });


        //6번 하트클릭
        db.collection("SocialBoard")
                .whereEqualTo("load", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_btn6 = findViewById(R.id.board_heart_6);
                                heart_btn6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DocumentReference washingtonRef = db.collection("SocialBoard").document(id6);

                                        makeText(getApplicationContext(), "좋아요 1증가", Toast.LENGTH_SHORT).show();

                                        washingtonRef
                                                .update("boardLike", heart6+1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        //Like 바꾸기
                                                        txt3 = (TextView) findViewById(R.id.board_heart_6) ;
                                                        heart6+=1;
                                                        txt3.setText(heart6.toString()) ;
                                                        Update_Ranking(id6, heart6);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        //startToast("Error updating document");
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    }
                });

        //첫번째 버튼 클릭
        btn1 = findViewById(R.id.board_w1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn1_intent = new Intent(F_SocialB.this, G_Social_board_enter_writing.class);
                //첫번째 버튼 클릭 인텐트 정보 전달
                btn1_intent.putExtra("throw", Title_idlist.get(0));
                startActivity(btn1_intent);
                finish();
            }
        });

        //두번째 버튼 클릭
        btn2 = findViewById(R.id.board_w2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn2_intent = new Intent(F_SocialB.this, G_Social_board_enter_writing.class);
                //두번째 버튼 클릭 인텐트 정보 전달
                btn2_intent.putExtra("throw", Title_idlist.get(1));
                startActivity(btn2_intent);
                finish();
            }
        });

        //세번째 버튼 클릭
        btn3 = findViewById(R.id.board_w3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn3_intent = new Intent(F_SocialB.this, G_Social_board_enter_writing.class);
                //세번째 버튼 클릭 인텐트 정보 전달
                btn3_intent.putExtra("throw", Title_idlist.get(2));
                startActivity(btn3_intent);
                finish();
            }
        });

        //네번째 버튼 클릭
        btn3 = findViewById(R.id.board_w4);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn3_intent = new Intent(F_SocialB.this, G_Social_board_enter_writing.class);
                //세번째 버튼 클릭 인텐트 정보 전달
                btn3_intent.putExtra("throw", Title_idlist.get(3));
                startActivity(btn3_intent);
                finish();
            }
        });

        //다섯번째 버튼 클릭
        btn3 = findViewById(R.id.board_w5);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn3_intent = new Intent(F_SocialB.this, G_Social_board_enter_writing.class);
                //세번째 버튼 클릭 인텐트 정보 전달
                btn3_intent.putExtra("throw", Title_idlist.get(4));
                startActivity(btn3_intent);
                finish();
            }
        });

        //여섯번째 버튼 클릭
        btn3 = findViewById(R.id.board_w6);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn3_intent = new Intent(F_SocialB.this, G_Social_board_enter_writing.class);
                //세번째 버튼 클릭 인텐트 정보 전달
                btn3_intent.putExtra("throw", Title_idlist.get(5));
                startActivity(btn3_intent);
                finish();
            }
        });

        //글쓰기
        go_pencil = findViewById(R.id.board_pencil);
        go_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(F_SocialB.this, G_Socialpencil.class);
                startActivity(intent);
                finish();

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
}