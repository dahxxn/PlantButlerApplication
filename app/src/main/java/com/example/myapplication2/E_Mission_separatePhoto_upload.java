package com.example.myapplication2;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class E_Mission_separatePhoto_upload extends AppCompatActivity {

    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("ImageConfirm");
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    ImageView imageView;
    Button gallery_btn;
    Button regist_btn;
    Uri uri;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageList imageList;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_separate_photo_upload);
        imageView = findViewById(R.id.Gallery_Image);
        //firebase정보를 ImageList.java로 가져온 뒤 list에 이미지 추가
        DocumentReference docRef = db.collection("ImageList").document("list");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                imageList= documentSnapshot.toObject(ImageList.class);
            }
        });
        //갤러리 버튼 부분
        gallery_btn = (Button) findViewById((R.id.gallery_btn));{
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent , 1);
            }
        });
    };

        regist_btn = (Button) findViewById(R.id.register_btn);{
        regist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri != null)
                {
                    uploadToFirebase(uri);
                } else{
                    Toast.makeText(E_Mission_separatePhoto_upload.this , "사진을 선택해주세요" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), E_Mission_selectActivity.class);
                startActivity(intent);
            }
        });
    }

    // 파이어베이스 이미지 업로드
    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis()+"." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelid = root.push().getKey();
                        root.child(modelid).setValue(model);
                        Toast.makeText(E_Mission_separatePhoto_upload.this,"업로드 성공!!" , Toast.LENGTH_SHORT).show();
                        // StorageReference storageRef = storage.getReference();
                        // startToast(fileRef.getName());
                        imageView.setImageResource(R.drawable.picture);

                        //firestore에 Image : Picture 저장---
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();
                        String imageName= fileRef.getName();
                        ImageConfirm image = new ImageConfirm(uid, imageName);
                        db.collection("ImageConfirm").document(imageName)
                                .set(image);

                        //firestore에 ImageList : Picture 저장---
                        imageList.addList(imageName);
                        db.collection("ImageList").document("list")
                                .set(imageList);
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
                Toast.makeText(E_Mission_separatePhoto_upload.this , "업로드 실패.." , Toast.LENGTH_SHORT).show();
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

        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    imageView.setImageURI(uri);
                }
                break;
        }

    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , E_Mission_selectActivity.class);
        startActivity(intent);
        finish();
    }
}
