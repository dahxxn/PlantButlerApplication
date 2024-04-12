package com.example.myapplication2;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;

public class G_Socialpencil extends AppCompatActivity {

    Button enter,back_btn;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsocialpencil);

        FirebaseUser user = mAuth.getCurrentUser();

        back_btn = findViewById(R.id.g_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(G_Socialpencil.this, F_SocialB.class);
                startActivity(intent);

            }
        });
        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input_title = ((EditText) findViewById(R.id.input_title)).getText().toString();
                String input_content = ((EditText) findViewById(R.id.input_content)).getText().toString();

                LocalDate now;
                String board_id = user.getUid().concat(input_title);


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    now = LocalDate.now();
                    SocialBoard social = new SocialBoard(board_id, user.getUid(), input_title, input_content, now.toString(), 0);
                    db.collection("SocialBoard").document(user.getUid())
                            .set(social)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) { }
                            });
                }
//                SocialBoard social = new SocialBoard(, user.getUid(), input_title, input_content, , 0);

                Intent intent = new Intent(G_Socialpencil.this, F_SocialB.class);
                startActivity(intent);
                finish();


            }
        });
    }
}