
package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class E_MyPage_customActivity extends AppCompatActivity {

    private ImageView currentPlant_btn,currentPlant_pot;
    private TextView currentPlant_name;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private UserItem userItem = new UserItem(user.getUid());
    private ImageView plant_pot;
    private int potbox[]=new int[6];
    private int setPotNum;
    private int pot_rid[] = {R.id.plant1_pot, R.id.plant2_pot, R.id.plant3_pot, R.id.plant4_pot, R.id.plant5_pot, R.id.plant6_pot};
    private String potId[] = {"pot_1","pot_2","pot_3","pot_4","pot_5","pot_6"};
    private String currentPlantName,currentPlantType,currentPlantPot;
    private String plantID;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_mypage_custom);
        plantID = getIntent().getStringExtra("plantID");

        //이전 버튼 추가
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), D_MyPageActivity.class);
                startActivity(intent);
            }
        });

        currentPlant_btn = (ImageView) findViewById(R.id.custom_currentPlant);
        currentPlant_pot = (ImageView) findViewById(R.id.custom_currentPlant_pot);

        //startToast(plantID);

        db.collection("UserPlant")
                .whereEqualTo("userID", user.getUid())
                .whereEqualTo("plantID", plantID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                currentPlantName = document.getData().get("plantName").toString();
                                currentPlantType=document.getData().get("plantType").toString();
                                currentPlantPot= document.getData().get("potType").toString();
                            }
                            currentPlant_name = (TextView) findViewById(R.id.register_customPlantName);
                            currentPlant_name.setText(currentPlantName);


                            db.collection("User")
                                    .whereEqualTo("userID", user.getUid())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                    String real_currentPlantName = document.getData().get("currentPlant").toString();
                                                    int currentPlantXP = Integer.parseInt(document.getData().get("currentPlantXP").toString());

                                                    if(real_currentPlantName.equals(currentPlantName)){
                                                        if(currentPlantType.equals("몬스테라")){
                                                            if(currentPlantXP <=30){
                                                                currentPlant_btn.setImageResource(R.drawable.monstera1);
                                                            }else if(currentPlantXP <=60){
                                                                currentPlant_btn.setImageResource(R.drawable.monstera2);
                                                            }else{
                                                                currentPlant_btn.setImageResource(R.drawable.monstera3);
                                                            }
                                                        }else if(currentPlantType.equals("해바라기")){
                                                            if(currentPlantXP <=30){
                                                                currentPlant_btn.setImageResource(R.drawable.sunflower1);
                                                            }else if(currentPlantXP <=60){
                                                                currentPlant_btn.setImageResource(R.drawable.sunflower2);
                                                            }else{
                                                                currentPlant_btn.setImageResource(R.drawable.sunflower3);
                                                            }
                                                        }

                                                    }else{
                                                        if (currentPlantType.equals("몬스테라")) {
                                                            currentPlant_btn.setImageResource(R.drawable.monstera3);
                                                        } else if (currentPlantType.equals("해바라기")) {
                                                            currentPlant_btn.setImageResource(R.drawable.sunflower3);
                                                        }
                                                    }

                                                    if (currentPlantPot.equals("pot_1")) {
                                                        currentPlant_pot.setImageResource(R.drawable.pot1);
                                                    } else if (currentPlantPot.equals("pot_2")) {
                                                        currentPlant_pot.setImageResource(R.drawable.pot2);
                                                    } else if (currentPlantPot.equals("pot_3")) {
                                                        currentPlant_pot.setImageResource(R.drawable.pot3);
                                                    }
                                                }
                                            }

                                        }
                                    });




                        }
                    }
                });
        potActivity(); //userItem 객체 업데이트
    }

    public void potActivity() {
        if (user != null) { //로그인된 사용자 있음
            //UserItem 객체 업데이트
            DocumentReference docRef2 = db.collection("UserItem").document(user.getUid());
            docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userItem = documentSnapshot.toObject(UserItem.class);

                    //구매한 화분 목록 나타내기
                    for(int pot_i=0, rid_j=0; pot_i<6; pot_i++){
                        if(userItem.get_potItem_b(potId[pot_i])) { //true면 pot아이템 가지고 있음: 사용 가능
                            plant_pot = (ImageView) findViewById(pot_rid[rid_j]);
                            resourceImage(pot_i);
                            potbox[rid_j]=pot_i;
                            rid_j++;
                        }
                        else{}//false면 pot아이템 안가지고 있음 다음탐색
                    }
                }
            });
        } else { //로그인된 사용자 없음
            startToast("No user is signed in");
        }


    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveBtn:
                save();
                break;
            case R.id.plant1_pot:
                setPot(0); //몇번재 rid선택했는지 알려줌 = box[i]의 i와 같다.
                break;
            case R.id.plant2_pot:
                setPot(1);
                break;
            case R.id.plant3_pot:
                setPot(2);
                break;
            case R.id.plant4_pot:
                setPot(3);
                break;
            case R.id.plant5_pot:
                setPot(4);
                break;
            case R.id.plant6_pot:
                setPot(5);
                break;

        }
    };
    public void save(){
        db.collection("UserPlant")
                .whereEqualTo("userID", user.getUid())
                .whereEqualTo("plantID", plantID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                document.getReference().delete();
                            }

                            UserPlant usp = new UserPlant(plantID,user.getUid(),currentPlantType,currentPlantName,potId[setPotNum]);
                            db.collection("UserPlant").add(usp);

                        }
                    }
                });
        startToast("저장되었습니다.");

    }

    public void setPot(int i){ //화분 선택하면 식물에게 적용해 보이기
        plant_pot = currentPlant_pot;
        resourceImage(potbox[i]);
        setPotNum=potbox[i]; //바뀐 화분 아이디 번호
        //startToast("resourceImage"+setPotNum);
    }

    public void resourceImage(int i){

        if(i==0){
            plant_pot.setImageResource(R.drawable.pot1);
        }
        else if(i==1){
            plant_pot.setImageResource(R.drawable.pot2);
        }
        else if(i==2){
            plant_pot.setImageResource(R.drawable.pot3);
        }
        else if(i==3){
            plant_pot.setImageResource(R.drawable.pot4);
        }
        else if(i==4){
            plant_pot.setImageResource(R.drawable.pot5);
        }
        else if(i==5){
            plant_pot.setImageResource(R.drawable.pot6);
        }
    }

    public void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent (this , D_MyPageActivity.class);
        startActivity(intent);
        finish();
    }

}