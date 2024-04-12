package com.example.myapplication2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class E_Store_nutrientActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Dialog dialog1;
    private TextView textView1;
    private UserItem userItem = new UserItem(user.getUid());
    private int point;
    private ItemInfo itemInfo = new ItemInfo();
    private User userInfo;
    private int count;
    private Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_store_nutrient);

        //뒤로가기
        back_btn=findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(E_Store_nutrientActivity.this, D_StoreActivity.class);
                startActivity(intent);
            }
        });

        nutrientActivity();

        dialog1 = new Dialog(E_Store_nutrientActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.dialog_item_purchase);
        textView1 = (TextView) dialog1.findViewById(R.id.register_item_name) ;
    }
    public void nutrientActivity(){
        if (user != null) { //로그인된 사용자 있음

            //User 객체 업데이트
            DocumentReference docRef = db.collection("User").document(user.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userInfo= documentSnapshot.toObject(User.class);
                    point = userInfo.getCoin();
                    String txt = String.valueOf(point)+"P";
                    //startToast(String.valueOf(point));
                    TextView textView2 = (TextView) findViewById(R.id.store_coin);
                    textView2.setText(txt);
                }
            });

            //UserItem 객체 업데이트
            DocumentReference docRef2 = db.collection("UserItem").document(user.getUid());
            docRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userItem= documentSnapshot.toObject(UserItem.class);
                }
            });

        } else{ //로그인된 사용자 없음
            startToast("No user is signed in");
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.store_nutrient_item1_img:
                show_popup(0,"nutrient_1");
                break;
            case R.id.store_nutrient_item2_img:
                show_popup(1,"nutrient_2");
                break;
            case R.id.store_nutrient_item3_img:
                show_popup(2,"nutrient_3");
                break;
            case R.id.store_nutrient_item4_img:
                show_popup(3,"nutrient_4");
                break;
            case R.id.store_nutrient_item5_img:
                show_popup(4,"nutrient_5");
                break;
            case R.id.store_nutrient_item6_img:
                show_popup(5,"nutrient_6");
                break;
        }
    };

    public void show_popup(int tmp, String itemID) {
        dialog1.show();

        String nutrient_name=itemInfo.getNutrientName(tmp);
        textView1.setText(nutrient_name) ;

        Button no_btn = dialog1.findViewById(R.id.nobtn);
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        Button yes_btn = dialog1.findViewById(R.id.yesbtn);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item_price=itemInfo.getNutrientPrice(tmp);
                if(point<item_price){ //코인 부족
                    startToast("coin 부족 : 구매실패");
                    dialog1.dismiss();
                }
                else{
                    startToast("구매완료");

                    //point 차감
                    point-=item_price;
                    userInfo.setCoin(point);

                    //User: coin 업데이트
                    DocumentReference washingtonRef1 = db.collection("User").document(user.getUid());
                    washingtonRef1
                            .update("coin",point);

                    //UserItem: 아이템 개수 업데이트
                    count = userItem.get_nutrientItem_count(itemID);
                    count+=1;
                    userItem.increase_nutrientItem(itemID,count);
                    userItem.increase_itemCount(itemID, count);
                    db.collection("UserItem").document(user.getUid())
                            .set(userItem);

                    dialog1.dismiss();
                    nutrientActivity();
                }
            }
        });
    }


    public void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
