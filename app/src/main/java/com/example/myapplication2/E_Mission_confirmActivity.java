package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.*;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class E_Mission_confirmActivity extends AppCompatActivity {
    String image1,image2 , image3,image4,image5,image6,image7,image8,image9;
    ImageView Img1 , Img2 , Img3,Img4,Img5,Img6,Img7,Img8,Img9;
    int i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0,i9=0;
    Button confirmBtn, declarationBtn , backBtn;
    ArrayList<ImageView> Select_Img_Array = new ArrayList<ImageView>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userRef;
    ImageList imageList = new ImageList();
    ArrayList<String> list = new ArrayList<>();
    int listLength;
    int []randomArr = new int[9];
    FirebaseUser user;
    Dialog ConfirmDialog;
    Button yesBtn;
    Button BackBtn;
    private FirebaseAuth mAuth;
    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_confirm);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("ImageList").document("list");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                imageList= documentSnapshot.toObject(ImageList.class);
                listLength =imageList.arrLength();
                list =imageList.getImageList();
                int []randomArr= new int[9];
                for(int i=0; i<9; i++) {
                    int a;
                    Random r = new Random();
                    a = r.nextInt(listLength);
                    randomArr[i] = a;
                }
                ImageSetting1(randomArr[0]);
                ImageSetting2(randomArr[1]);
                ImageSetting3(randomArr[2]);
                ImageSetting4(randomArr[3]);
                ImageSetting5(randomArr[4]);
                ImageSetting6(randomArr[5]);
                ImageSetting7(randomArr[6]);
                ImageSetting8(randomArr[7]);
                ImageSetting9(randomArr[8]);




            }
        });

        Img1 = (ImageView)findViewById(R.id.ImageBtn1);
        Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i1 = 1- i1;
                if(i1 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img1.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[0]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(0));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }
            }
        });

        Img2 = (ImageView)findViewById(R.id.ImageBtn2);
        Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i2 = 1- i2;
                if(i2 == 1) {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img2.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[1]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(1));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }

            }
        });

        Img3 = (ImageView)findViewById(R.id.ImageBtn3);
        Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i3 = 1- i3;
                if(i3 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img3.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[2]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(2));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }

            }
        });

        Img4 = (ImageView)findViewById(R.id.ImageBtn4);
        Img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i4 = 1 - i4;
                if (i4 == 1) {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img4.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[3]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(3));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }
            }
        });

        Img5 = (ImageView)findViewById(R.id.ImageBtn5);
        Img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i5 = 1- i5;
                if(i5 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img5.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[4]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(4));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }

            }
        });
        Img6 = (ImageView)findViewById(R.id.ImageBtn6);
        Img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i6 = 1- i6;
                if(i6 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img6.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[5]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(5));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }

            }
        });
        Img7 = (ImageView)findViewById(R.id.ImageBtn7);
        Img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i7 = 1- i7;
                if(i7 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img7.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[6]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(6));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }


            }
        });
        Img8 = (ImageView)findViewById(R.id.ImageBtn8);
        Img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i8 = 1- i8;
                if(i8 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img8.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[7]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(7));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }

            }
        });
        Img9 = (ImageView)findViewById(R.id.ImageBtn9);
        Img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i9 = 1- i9;
                if(i1 == 1)
                {
                    ConfirmDialog = new Dialog(E_Mission_confirmActivity.this);
                    ConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ConfirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ConfirmDialog.setCancelable(false);
                    ConfirmDialog.setContentView(R.layout.confirm_dialog);
                    ConfirmDialog.show();
                    yesBtn = ConfirmDialog.findViewById(R.id.yesbtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Img9.setImageResource(R.drawable.checkimage);
                            db.collection("ImageConfirm")
                                    .whereEqualTo("imageName", list.get(randomArr[8]))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                userRef = db.collection("ImageConfirm").document(list.get(8));
                                                userRef.update("check" , "T");
                                                Toast.makeText(E_Mission_confirmActivity.this , "인증 되었습니다!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            ConfirmDialog.dismiss();
                        }
                    });
                    BackBtn = ConfirmDialog.findViewById(R.id.nobtn);
                    BackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ConfirmDialog.dismiss();
                        }
                    });
                }

            }
        });

        //인증 버튼

        //이전 버튼
        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), D_MissionActivity.class);
                startActivity(intent);
            }
        });
        //신고하기 버튼
        declarationBtn = (Button) findViewById(R.id.deBtn);
        declarationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),E_Mission_declaration.class);
                startActivity(intent);
            }
        });

    }




    private void ImageSetting1(int tmp){
        ImageView imageBtn1;
        imageBtn1 = (ImageView) findViewById(R.id.ImageBtn1);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting2(int tmp){
        ImageView imageBtn2;
        imageBtn2 = (ImageView) findViewById(R.id.ImageBtn2);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn2);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting3(int tmp){
        ImageView imageBtn3;
        imageBtn3 = (ImageView) findViewById(R.id.ImageBtn3);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn3);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting4(int tmp){
        ImageView imageBtn4;
        imageBtn4 = (ImageView) findViewById(R.id.ImageBtn4);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn4);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting5(int tmp){
        ImageView imageBtn5;
        imageBtn5 = (ImageView) findViewById(R.id.ImageBtn5);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn5);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting6(int tmp){
        ImageView imageBtn6;
        imageBtn6 = (ImageView) findViewById(R.id.ImageBtn6);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn6);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting7(int tmp){
        ImageView imageBtn7;
        imageBtn7 = (ImageView) findViewById(R.id.ImageBtn7);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn7);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting8(int tmp){
        ImageView imageBtn8;
        imageBtn8 = (ImageView) findViewById(R.id.ImageBtn8);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn8);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void ImageSetting9(int tmp){
        ImageView imageBtn9;
        imageBtn9 = (ImageView) findViewById(R.id.ImageBtn9);

        String imageName = list.get(tmp);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(imageName);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(E_Mission_confirmActivity.this).load(uri).into(imageBtn9);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , D_MissionActivity.class);
        startActivity(intent);
        finish();
    }
}