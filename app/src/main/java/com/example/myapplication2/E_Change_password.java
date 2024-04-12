package com.example.myapplication2;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class E_Change_password extends AppCompatActivity {

    Button change_password, back;
    EditText input1;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user ;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echange_password);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        //비밀번호 불러오기
        db.collection("User")
                .whereEqualTo("userID",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                try{
                                    //비밀번호 불러오기
                                    password = document.getData().get("password").toString();

                                    TextView textView2 = (TextView) findViewById(R.id.origin_password) ;
                                    textView2.setText("origin password : "+ password) ;

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
                Intent intent = new Intent(E_Change_password.this, C_MainActivity.class);
                startActivity(intent);
            }
        });
        //비밀번호 변경 버튼클릭
        DocumentReference washingtonRef = db.collection("User").document(user.getUid());
        change_password = (Button) findViewById(R.id.password_change_btn);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String i1 = ((EditText) findViewById(R.id.change_password1)).getText().toString();
                String i2 = ((EditText) findViewById(R.id.change_password2)).getText().toString();

                try {
                    if (i1.equals(i2)) {

                        //authentication쪽
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String newPassword = i1;

                        user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            makeText(getApplicationContext(), "비밀번호변경성공", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(E_Change_password.this, D_Profile.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });


                        //firestore쪽
                        washingtonRef
                                .update("password", i1)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        Intent intent = new Intent(E_Change_password.this, D_Profile.class);
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
                    } else {
                        makeText(getApplicationContext(), "비밀번호를 알맞게 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    makeText(getApplicationContext(), "비밀번호는 공백이 될 수 없습니다", Toast.LENGTH_LONG).show();
                }
            }
        });


    } //onCreate

}