package com.example.myapplication2;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class E_Change_nickname extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user ;

    String nickname, email, password, profileurl;
    Button change, back;
    EditText input1, input2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echange_nickname);


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

                                    //닉네임 불러오기
                                    nickname = document.getData().get("nickName").toString();

                                    TextView textView2 = (TextView) findViewById(R.id.origin_nickname) ;
                                    textView2.setText("origin name : "+ nickname) ;

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
                Intent intent = new Intent(E_Change_nickname.this, C_MainActivity.class);
                startActivity(intent);
            }
        });
        //확인버튼클릭
        change = (Button) findViewById(R.id.email_change_btn);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //닉네임만 변경하기

                String i1 = ((EditText) findViewById(R.id.change_nickname1)).getText().toString();
                String i2 = ((EditText) findViewById(R.id.change_nickname2)).getText().toString();
                DocumentReference washingtonRef = db.collection("User").document(user.getUid());


                if (i1.equals(i2)) {
                    makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                    washingtonRef
                            .update("nickName", i1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    Intent intent = new Intent(E_Change_nickname.this, D_Profile.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //startToast("Error updating document");
                                }
                            });

                } else if (i1.equals("") || i2.equals("")) {
                    makeText(getApplicationContext(), "닉네임이 공백입니다", Toast.LENGTH_SHORT).show();
                } else { //닉네임 input이 다를때
                    makeText(getApplicationContext(), "닉네임을 알맞게 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }//oncreate
}
