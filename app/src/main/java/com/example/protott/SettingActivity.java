package com.example.protott;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingActivity extends AppCompatActivity {

    private Button btn_logout;
    private Button btn_passwordreset;
    private Button main_mb_btn;
    private FirebaseAuth mFirebaseAuth;

   /* @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button btn_logout = findViewById(R.id.btn_logout);
        Button btn_passwordreset = findViewById(R.id.btn_passwordreset);
        Button main_mb_btn = findViewById(R.id.main_mb_btn);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

       if(user == null)
        {
            Toast.makeText(SettingActivity.this, "정보가 없는 회원입니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            DocumentReference docRef = db.collection("user").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document!=null)
                        {

                            if (document.exists()) {
                                Toast.makeText(SettingActivity.this, "자동로그인.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(SettingActivity.this, MemberInitActivity.class);
                                startActivity(intent);
                                Toast.makeText(SettingActivity.this, "개인정보를 등록해주세요~.", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                }
            });

        }


        main_mb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MemberInitActivity.class);
                startActivity(intent);
                finish();


            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                mFirebaseAuth.signOut();
                finish();

            }
        });

        //비번 변경 버튼 누를때
        btn_passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Password_resetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}