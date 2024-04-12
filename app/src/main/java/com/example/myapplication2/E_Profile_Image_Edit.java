package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class E_Profile_Image_Edit extends AppCompatActivity {

    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("image");
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    ImageView imageView;
    Button gallery_btn;
    Button regist_btn, ok_btn, test_button;
    Uri uri;
    String email, profileImage, User_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eprofile_image_edit);

        imageView = findViewById(R.id.Gallery_Image);
        //갤러리 버튼 부분
        gallery_btn = (Button) findViewById((R.id.gallery_btn));
        {
            gallery_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);
                }
            });
        }
        ;


        //등록버튼
        regist_btn = (Button) findViewById(R.id.register_btn);
        {
            regist_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (uri != null) {
                        uploadToFirebase(uri);
                    } else {
                        Toast.makeText(E_Profile_Image_Edit.this, "사진을 선택해주세요", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        //확인버튼
        ok_btn = (Button) findViewById(R.id.ok);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(E_Profile_Image_Edit.this, D_Profile.class);
                startActivity(intent);
                finish();

            }
        });

        //사용자 정보 불러오기
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        db.collection("User")
                .whereEqualTo("userID", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {

                                    //이메일 불러오기
                                    email = document.getData().get("email").toString();
                                    profileImage = document.getData().get("profileImage").toString();

                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                });


    } //oncreate


    // 파이어베이스 이미지 업로드
    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(user.getUid() + "Profile.jpg");

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelid = root.push().getKey();
                        root.child(modelid).setValue(model);
                        Toast.makeText(E_Profile_Image_Edit.this, "업로드 성공!!", Toast.LENGTH_SHORT).show();
                        profileImage = user.getUid() + "Profile.jpg";
                        imageView.setImageResource(R.drawable.picture);

                        //profileImageurl변경부분
                        db.collection("User").document(user.getUid())
                                .update("profileImage", profileImage);

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(E_Profile_Image_Edit.this, "업로드 실패..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //파일 타입 정하는 함수
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    //갤러리에서 사진가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    imageView.setImageURI(uri);
                }
                break;
        }
    }
}