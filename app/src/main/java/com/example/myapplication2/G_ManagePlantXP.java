package com.example.myapplication2;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class G_ManagePlantXP {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    G_ManagePlantXP(){
    }

    public void add_XP(String userID, int xp_value){

        db.collection("User")
                .whereEqualTo("userID",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int currentPlantXP = Integer.parseInt( document.getData().get("currentPlantXP").toString()) ;

                                int set_value = currentPlantXP + xp_value;
                                userRef = db.collection("User").document(userID);

                                // xp = 100이면 성장 완료 => currentPlant = ""로 설정 후 토스트 띄우기
                                if(set_value >=100){
                                    set_value = 0;
                                    userRef.update("currentPlant", "");
                                    userRef.update("currentPlantXP", set_value);
                                }else{
                                    userRef.update("currentPlantXP", set_value);
                                }

                            }
                        }
                    }
                });
    }

    public void delete_XP(String userID){
        db.collection("User")
                .whereEqualTo("userID",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int currentPlantXP = (int) document.getData().get("currentPlantXP");

                                int set_value = 0;
                                userRef = db.collection("User").document(userID);
                                userRef.update("currentPlantXP", 0);
                            }
                        }
                    }
                });
    }
}
