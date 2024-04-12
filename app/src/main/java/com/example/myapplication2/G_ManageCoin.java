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

public class G_ManageCoin {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    G_ManageCoin(){}

    public void add_Coin(String userID, int coin){

        db.collection("User")
                .whereEqualTo("userID",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int currentCoin = Integer.parseInt( document.getData().get("coin").toString()) ;
                                int set_value = currentCoin + coin;
                                userRef = db.collection("User").document(userID);
                                userRef.update("coin",set_value);

                            }
                        }
                    }
                });
    }

    public void delete_Coin(String userID){
        db.collection("User")
                .whereEqualTo("userID",userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int currentCoin = Integer.parseInt( document.getData().get("coin").toString()) ;
                                int set_value = 0;
                                userRef = db.collection("User").document(userID);
                                userRef.update("coin",0);
                            }
                        }
                    }
                });
    }
}
